package com.hakimen.nodeImageEditor.core.notifications;

import com.hakimen.engine.core.utils.Mathf;
import com.hakimen.nodeImageEditor.core.notifications.notification.Notification;

import java.util.ArrayList;

public class NotificationHandler {


    public static final int NOTIFY_SHORT = 60 * 5;
    public static final int NOTIFY_NORMAL = 60 * 10;
    public static final int NOTIFY_BIG = 60 * 15;
    ArrayList<Notification> notifications = new ArrayList<>();

    float bias;

    public NotificationHandler(float bias) {
        this.bias = bias;
    }

    public void push(Notification notification){
        notifications.add(notification);
    }

    public void update(){
        for (int i = 0; i < notifications.size(); i++) {
            var notification = notifications.get(i);
            notification.update();
            if(notification.maxTimeAlive - notification.timeAlive == 0 ){
                notifications.remove(notification);
            }
        }
    }

    public void render(){
        for (int i = 0; i < notifications.size(); i++) {
            var notification = notifications.get(i);
            var baseY = 720 - 78 - i * 78;
            var baseX = 1280;
            if(notification.timeAlive > 0 && notification.timeAlive <= 10){
                baseX -= Mathf.sigmoidCurve(notification.timeAlive,bias) * 260;
            }else if(notification.timeAlive > 10 && notification.timeAlive < notification.maxTimeAlive - 10){
                baseX -= 260;
            }
            if(notification.maxTimeAlive - notification.timeAlive < 10){
                baseX = (int) (1280 - 260f + Mathf.sigmoidCurve(notification.timeAlive-notification.maxTimeAlive,bias) * 260);
            }
            notification.render(baseX, baseY);
        }
    }
}
