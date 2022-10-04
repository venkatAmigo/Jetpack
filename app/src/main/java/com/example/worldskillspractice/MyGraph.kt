package com.example.worldskillspractice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class MyGraph(context: Context,attributeSet: AttributeSet): View(context,attributeSet) {
    var dataset = mutableListOf<DataPointValues>()

    private var xMin =0
    private var xMax =0
    private var yMin =0
    private var yMax =0

    fun setData(newDataSet: List<DataPointValues>){
        xMin = newDataSet.minBy { it.xVal }.xVal
        xMax = newDataSet.maxBy { it.xVal }.xVal
        yMin = newDataSet.minBy { it.yVal }.yVal
        yMax = newDataSet.maxBy { it.yVal }.yVal

        dataset.clear()
        dataset.addAll(newDataSet)
        invalidate()
    }
    val drawLine = Paint().apply {
        color =  Color.RED
        isAntiAlias = true
        strokeWidth = 7f
    }
    val drawAxisLine = Paint().apply {
        color =  Color.BLUE
        strokeWidth = 7f
    }
    private val dataPointPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 7f
        style = Paint.Style.STROKE
    }

    private val dataPointFillPaint = Paint().apply {
        color = Color.WHITE
    }
    private val drawT = Paint().apply {
        color = Color.BLACK
        textSize = 26f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        dataset.forEachIndexed{
            index,point ->
            val realX = point.xVal.toRealX()
            val realY = point.yVal.toRealY()
            if(index< dataset.size - 1){
                val stX = point.xVal.toRealX()
                val stY = point.xVal.toRealY()
                val enX = dataset[index+1].xVal.toRealX()
                val enY = dataset[index+1].yVal.toRealY()
                canvas.drawLine(stX,stY,enX,enY,drawLine)
            }
            canvas.drawCircle(realX, realY, 7f, dataPointFillPaint)
            canvas.drawCircle(realX, realY, 7f, dataPointPaint)
        }
        canvas.drawLine(10f, 0f, 10f, height.toFloat(), drawAxisLine)
        canvas.drawLine(10f, height.toFloat(), width.toFloat(), height.toFloat(), drawAxisLine)
        canvas.drawText("1",20f,20f,drawT)
    }

    fun Int.toRealX() = toFloat()/ xMax* width
    fun Int.toRealY() = toFloat()/ yMax* height

}

data class DataPointValues(val xVal:Int,val yVal:Int)