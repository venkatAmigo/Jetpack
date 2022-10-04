package com.example.worldskillspractice

import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs

open class MyGesture(context: Context):OnTouchListener {

    val gestureDetector:GestureDetector
    init {
        gestureDetector = GestureDetector(context,GestureListener())
    }
    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(p1)
    }
    internal inner class GestureListener : SimpleOnGestureListener() {
        val VELOCITY = 100
        val THRESHOLD = 10
        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            val diffX = e2.x - e1.x
            val diffY = e2.y - e2.y
            if(abs(diffX)>abs(diffY)){
                if(abs(diffX)>THRESHOLD && velocityX > VELOCITY){
                    if(diffX < 0){
                        swipeLeft()
                    }else{

                    }
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }
    open fun swipeLeft(){

    }
}