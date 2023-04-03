package com.hakimen.engine.core.utils;

import com.hakimen.engine.core.io.Keyboard;
import com.hakimen.engine.core.io.Mouse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.function.Consumer;

public class Window {

    private static boolean sync;
    private static boolean fullscreen;

    public static boolean isSync() {
        return sync;
    }

    public static void setSync(boolean sync) {
        Window.sync = sync;
    }

    public static boolean isFullscreen() {
        return fullscreen;
    }

    public static void setFullscreen(boolean fullscreen) {
        Window.fullscreen = fullscreen;
    }

    //Render frame buffer and image
    private static BufferedImage render;
    private static int[] renderBuffer;

    private static JFrame frame;
    private static Canvas canvas;
    private static BufferStrategy bufferStrat;

    public static int width, height;
    public static int renderWidth, renderHeight;

    public static boolean hasFocus;

    public static int frames, ticks, fps, tps;
    private static long lastSec, lastFrame, frameTime, tickRemaining;
    public static int rectWidth, rectHeight;
    public static double scalingFactor;
    private static boolean close;


    public static void init(String name, int width,int height, int renderWidth,int renderHeight, boolean vSync,boolean fullscreen){

        Window.sync = vSync;
        Window.fullscreen = fullscreen;
        Window.width = width;
        Window.height = height;
        Window.renderWidth = renderWidth;
        Window.renderHeight = renderHeight;

        Window.lastSec = Time.now();
        Window.lastFrame = Time.now();

        GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration();
        Window.render = gfxConfig.createCompatibleImage(renderWidth,renderHeight, Transparency.OPAQUE);
        Window.renderBuffer = ((DataBufferInt)Window.render.getRaster().getDataBuffer()).getData();

        //Make Window

        Window.frame = new JFrame(name);
        Window.frame.getContentPane().setPreferredSize(new Dimension(width,height));
        Window.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Window.frame.setResizable(true);

        Window.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Window.close = true;
            }

            @Override
            public void windowGainedFocus(WindowEvent e) {
                super.windowGainedFocus(e);
                Window.hasFocus = true;
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                super.windowLostFocus(e);
                Window.hasFocus = false;
            }
        });
        Window.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                Window.width = Window.canvas.getWidth();
                Window.height = Window.canvas.getHeight();
            }
        });
        Window.frame.setIgnoreRepaint(true);


        //Canvas Setup
        Window.canvas = new Canvas(gfxConfig);
        Window.canvas.setPreferredSize(new Dimension(width,height));
        Window.canvas.setSize(width,height);
        Window.canvas.setIgnoreRepaint(true);

        Window.canvas.addKeyListener(Keyboard.getListener());
        Window.canvas.addMouseListener(Mouse.getListener());
        Window.canvas.addMouseMotionListener(Mouse.getMoveListener());
        Window.canvas.addMouseWheelListener(Mouse.getWheelListener());

        Window.frame.add(canvas);
        Window.frame.pack();
        Window.frame.setLocationRelativeTo(null);
        Window.frame.setVisible(true);
        Window.canvas.setVisible(true);

        Window.canvas.createBufferStrategy(3);
        Window.bufferStrat = Window.canvas.getBufferStrategy();
    }

    public static boolean hasFocus() {
        return Window.frame.isActive();
    }

    public static void close() {
        Window.close = true;
        Window.frame.dispatchEvent(new WindowEvent(Window.frame, WindowEvent.WINDOW_CLOSING));
    }


    public static void render(){
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        if(fullscreen){
            device.setFullScreenWindow(Window.frame);
        }else{
            device.setFullScreenWindow(null);
        }

        Graphics graphics = bufferStrat.getDrawGraphics();

        double renderAspectRatio = (double) renderWidth / (double) renderHeight,
                windowAspectRatio = (double) Window.width / (double) Window.height;
                scalingFactor =  windowAspectRatio > renderAspectRatio ?
                        (double) Window.height / (double) renderHeight :
                        (double) Window.width / (double) renderWidth;
        rectWidth = (int) (renderWidth * scalingFactor);
        rectHeight = (int) (renderHeight * scalingFactor);
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0,Window.width, Window.height);
        graphics.drawImage(render,(Window.width - rectWidth)/2, (Window.height - rectHeight)/2, rectWidth,rectHeight,null);
        graphics.dispose();
        if(!Window.bufferStrat.contentsLost()){
            Window.bufferStrat.show();
        }
    }

    public static void loop(Runnable init, Runnable destroy, Runnable tick, Runnable update, Runnable render, Consumer<int[]> shader){
        init.run();

        while (!Window.close){
            long now = Time.now(), start = now;
            if (now - Window.lastSec >= Time.NS_PER_SECOND){
                Window.lastSec = now;
                Window.fps = Window.frames;
                Window.tps = Window.ticks;
                Window.frames = 0;
                Window.ticks = 0;
                System.out.println(Window.fps + " | " + Window.tps);
            }
            Window.frameTime = now - Window.lastFrame;
            Window.lastFrame = now;

            long tickTime = Window.frameTime + Window.tickRemaining;
            while (tickTime >= Time.NS_TICK) {
                tick.run();
                tickTime -= Time.NS_TICK;
                Window.ticks++;
            }
            Window.tickRemaining = tickTime;

            if(Window.ticks % 5 == 0){
                Mouse.mouseScrool = 0;
            }

            update.run();
            RenderUtils.g = Window.render.createGraphics();
            RenderUtils.FillRect(0,0,Window.renderWidth,Window.renderHeight,Color.black);
            render.run();
            shader.accept(renderBuffer);
            Window.render();
            Window.frames++;
            if(sync) {
                try {
                    long sleepMs = ((16 * Time.NS_PER_MILI_SECOND) - (Time.now() - start)) / Time.NS_PER_MILI_SECOND;
                    Thread.sleep(sleepMs < 0 ? 0 : sleepMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        destroy.run();
        System.exit(0);
    }
}
