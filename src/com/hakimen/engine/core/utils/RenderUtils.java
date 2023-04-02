package com.hakimen.engine.core.utils;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static com.hakimen.engine.core.utils.Mathf.map;

public class RenderUtils {
    public static Graphics2D g;

    public static void SetRenderHints(RenderingHints.Key key, Object value){
        g.setRenderingHint(key, value);
    }
    public static void Scale(float x,float y){
        g.scale(x,y);
    }
    public static void ClipShape(Shape s){
        g.clip(s);
    }

    public static void Translate(int dx,int dy){
        g.translate(dx,dy);
    }

    public static void Transform(AffineTransform at){
        g.transform(at);
    }

    public static void DrawRect(float x,float y, float dx, float dy,Color color){
        g.setColor(color);
        g.drawRect((int)x, (int)y, (int)dx, (int)dy);
    }

    public static void DrawRect(int x, int y, int dx, int dy, Color color) {
        DrawRect((float)x,(float)y,(float)dx,(float)dy,color);
    }

    public static void FillRect(float x,float y,float dx,float dy,Color color) {
        g.setColor(color);
        g.fillRect((int)x, (int)y, (int)dx, (int)dy);
    }
    public static void FillRect(int x, int y, int dx, int dy, Color color) {
        FillRect((float)x,(float)y,(float)dx,(float)dy,color);
    }

    public static void DrawRoundedRect(float x, float y, float dx, float dy, float arcWidth, float arcHeight, Color color) {
        g.setColor(color);
        g.drawRoundRect((int)x, (int)y, (int)dx, (int)dy, (int)arcWidth, (int)arcHeight);
    }
    public static void DrawRoundedRect(int x, int y, int dx, int dy, int arcWidth, int arcHeight, Color color) {
        DrawRoundedRect((float)x,(float)y,(float)dx,(float)dy,(float)arcWidth,(float)arcWidth,color);
    }

    public static void FillRoundedRect(float x, float y, float dx, float dy, float arcWidth, float arcHeight, Color color) {
        g.setColor(color);
        g.fillRoundRect((int)x, (int)y, (int)dx, (int)dy, (int)arcWidth, (int)arcHeight);
    }
    public static void FillRoundedRect(int x, int y, int dx, int dy, int arcWidth, int arcHeight, Color color) {
        FillRoundedRect((float)x,(float)y,(float)dx,(float)dy,(float)arcWidth,(float)arcWidth,color);
    }

    public static void DrawCircle(float x, float y, float radius, Color color) {
        g.setColor(color);
        g.drawOval((int)x, (int)y, (int)radius, (int)radius);
    }
    public static void DrawCircle(int x, int y, int radius, Color color) {
        DrawCircle((float)x,(float)y,(float)radius,color);
    }

    public static void FillCircle(float x, float y, float radius, Color color) {
        g.setColor(color);
        g.fillOval((int)x, (int)y, (int)radius, (int)radius);
    }
    public static void FillCircle(int x, int y, int radius, Color color) {
        FillCircle((float)x,(float)y,(float)radius,color);
    }

    public static void DrawOval(float x, float y, float dx, float dy, Color color) {
        g.setColor(color);
        g.drawOval((int)x, (int)y, (int)dx, (int)dy);
    }
    public static void DrawOval(int x, int y, int dx, int dy, Color color) {
        DrawOval((float)x, (float)y, (float)dx, (float)dy,color);
    }
    public static void FillOval(float x, float y, float dx, float dy, Color color) {
        g.setColor(color);
        g.fillOval((int)x, (int)y, (int)dx, (int)dy);
    }
    public static void FillOval(int x, int y, int dx, int dy, Color color) {
        FillOval((float)x, (float)y, (float)dx, (float)dy,color);
    }

    public static void DrawString(int x, int y,Color color, Object s) {
        g.setColor(color);
        g.drawString(s.toString(), x, y + 10);
    }

    public static void DrawFormattedString(int x, int y, Color color, String template, Object... data) {
        DrawString(x, y + 10, color, String.format(template, data));
    }

    public static void DrawImage(float x, float y, BufferedImage img) {
        g.drawImage(img,(int)x,(int)y,null);
    }
    public static void DrawImage(int x, int y, BufferedImage img) {
        DrawImage((float)x,(float)y,img);
    }
    public static void DrawImage(float x, float y,float width,float height, BufferedImage img) {
        g.drawImage(img,(int)x,(int)y,(int)width,(int)height,null);
    }
    public static void DrawImage(int x, int y, int width,int height,BufferedImage img) {
        DrawImage((float)x,(float)y,(float)width,(float)height,img);
    }

    public BufferedImage GetPartialSprite(int topX, int topY, int bottomX, int bottomY, BufferedImage img) {
        return img.getSubimage(topX, topY, bottomX, bottomY);
    }

    public static void DrawPixel(float x, float y, Color color) {
        g.setColor(color);
        g.drawLine((int)x, (int)y, (int)x, (int)y);
    }
    public static void DrawPixel(int x, int y, Color color) {
        DrawPixel((float)x, (float)y,color);
    }

    public static void DrawLine(float startX, float startY, float endX, float endY, Color color) {
        g.setColor(color);
        g.drawLine((int)startX, (int)startY, (int)endX, (int)endY);
    }
    public static void DrawLine(int startX, int startY, int endX, int endY, Color color) {
        DrawLine((float)startX, (float)startY, (float)endX, (float)endY, color);
    }

    public static void DrawArc(float x,float y,float sizeX,float sizeY,float angleStart, float angleEnd,Color c,Stroke s){
        g.setColor(c);
        g.setStroke(s);
        g.drawArc((int)x,(int)y,(int)sizeX,(int)sizeY,(int)angleStart,(int)angleEnd);
        g.setStroke(new BasicStroke());
    }
    public static void DrawArc(int x,int y,int sizeX,int sizeY,int angleStart, int angleEnd,Color c,Stroke s){
        DrawArc((float)x,(float)y,(float) sizeX,(float) sizeY,(float)angleStart,(float)angleEnd,c,s);
    }

    public static void FillArc(float x,float y,float sizeX,float sizeY,float angleStart, float angleEnd,Color c,Stroke s){
        g.setColor(c);
        g.setStroke(s);
        g.fillArc((int)x,(int)y,(int)sizeX,(int)sizeY,(int)angleStart,(int)angleEnd);
        g.setStroke(new BasicStroke());
    }
    public static void FillArc(int x,int y,int sizeX,int sizeY,int angleStart, int angleEnd,Color c,Stroke s){
        FillArc((float)x,(float)y,(float) sizeX,(float) sizeY,(float)angleStart,(float)angleEnd,c,s);
    }

    public static void DrawPolygon(Polygon poly, Color c) {
        g.setColor(c);
        g.drawPolygon(poly);
    }
    public static void FillPolygon(Polygon poly, Color c) {
        g.setColor(c);
        g.fillPolygon(poly);
    }

    public static void DrawTriangle(float p1x,float p1y,float p2x,float p2y,float p3x,float p3y,Color c){
        Polygon p = new Polygon();
        p.addPoint((int)p1x,(int)p1y);
        p.addPoint((int)p3x,(int)p3y);
        p.addPoint((int)p2x,(int)p2y);
        DrawPolygon(p,c);
    }
    public static void FillTriangle(float p1x,float p1y,float p2x,float p2y,float p3x,float p3y,Color c){
        Polygon p = new Polygon();
        p.addPoint((int)p1x,(int)p1y);
        p.addPoint((int)p3x,(int)p3y);
        p.addPoint((int)p2x,(int)p2y);
        FillPolygon(p,c);
    }
    public static void DrawTriangle(int p1x,int p1y,int p2x,int p2y,int p3x,int p3y,Color c){
        DrawTriangle((float)p1x,(float)p1y,(float)p2x,(float)p2y,(float)p3x,(float)p3y,c);
    }
    public static void FillTriangle(int p1x,int p1y,int p2x,int p2y,int p3x,int p3y,Color c){
        FillTriangle((float)p1x,(float)p1y,(float)p2x,(float)p2y,(float)p3x,(float)p3y,c);
    }

    public Color GetPixelColor(BufferedImage img, int x, int y) {
        int rgb = img.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        return new Color(red, green, blue);
    }
    public BufferedImage GetTintedImage(BufferedImage sprite, Color color) {
        BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(), BufferedImage.TRANSLUCENT);
        Graphics2D graphics = tintedSprite.createGraphics();
        graphics.drawImage(sprite, 0, 0, null);
        graphics.dispose();
        float r = map(color.getRed(), 0, 255, 0, 1), g = map(color.getGreen(), 0, 255, 0, 1), b = map(color.getBlue(), 0, 255, 0, 1), a = map(color.getAlpha(), 0, 255, 0, 1);
        for (int i = 0; i < tintedSprite.getWidth(); i++) {
            for (int j = 0; j < tintedSprite.getHeight(); j++) {
                int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().
                        getDataElements(i, j, null));
                rx *= r;
                gx *= g;
                bx *= b;
                ax *= a;
                tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
            }
        }
        return tintedSprite;
    }
}
