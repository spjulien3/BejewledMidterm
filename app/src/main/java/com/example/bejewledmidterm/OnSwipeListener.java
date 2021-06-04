package com.example.bejewledmidterm;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListener implements View.OnTouchListener {
    public GestureDetector gestureDetector;
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return gestureDetector.onTouchEvent(motionEvent);
    }
    public OnSwipeListener(Context context){
        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    private final class GestureListener extends GestureDetector.SimpleOnGestureListener{
        public static final int swipeThreshold = 100;
        public static final int swipeVelocityThreshold = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            float xDifference = e2.getX() - e1.getX();
            float yDifference = e2.getY() - e1.getY();
            if(Math.abs(xDifference)> Math.abs(yDifference)) {
                if (Math.abs(xDifference) > swipeThreshold && Math.abs(velocityX) > swipeVelocityThreshold) {
                    if (xDifference > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            }
            else if((Math.abs(yDifference)> swipeThreshold && Math.abs(velocityY)>swipeVelocityThreshold )){
                if(yDifference > 0){
                    onSwipeDown();
                }
                else {
                    onSwipeUp();
                }
                result = true;
            }
            return result;
        }
    }
    void onSwipeLeft(){}
    void onSwipeRight(){}
    void onSwipeUp(){}
    void onSwipeDown(){}
}
