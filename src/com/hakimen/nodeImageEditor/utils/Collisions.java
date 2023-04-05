package com.hakimen.nodeImageEditor.utils;

public class Collisions {
   public static boolean pointToCircle(float px, float py, float cx, float cy, float r) {

        // get distance between the point and circle's center
        // using the Pythagorean Theorem
        float distX = px - cx;
        float distY = py - cy;
        float distance = (float) Math.sqrt( (distX*distX) + (distY*distY) );

        // if the distance is less than the circle's
        // radius the point is inside!
       return distance <= r;
   }

   public static boolean pointToRect(float px, float py, float rx, float ry, float rw, float rh) {

        // is the point inside the rectangle's bounds?
       // above the bottom
       return px >= rx &&        // right of the left edge AND
               px <= rx + rw &&   // left of the right edge AND
               py >= ry &&        // below the top AND
               py <= ry + rh;
   }
}
