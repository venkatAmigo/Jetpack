package com.example.worldskillspractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.transition.Slide
import android.view.Gravity
import android.view.Gravity.END
import android.view.Window
import android.widget.TextView
import com.google.android.gms.maps.SupportMapFragment
import java.time.LocalDate
import java.util.*

class MapActivity : AppCompatActivity() {
    lateinit var mapFRagment : SupportMapFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTransition()
        setContentView(R.layout.activity_map)
        val dates = LocalDate.now().minusDays(1)
        val tv = findViewById<TextView>(R.id.textView)
        val graphView =  findViewById<MyGraph>(R.id.graph_view)
        val dataPointOne = DataPointValues(0,0)
        val dataPointTwo = DataPointValues(10,10)
        val dataPointThree = DataPointValues(30,5)
        val list = mutableListOf<DataPointValues>()
        list.add(dataPointOne)
        list.add(dataPointTwo)
        list.add(dataPointThree)
        graphView.setData(list)
        tv.text = dates.toString()
        mapFRagment = SupportMapFragment.newInstance()


    }
    private fun generateRandomDataPoints(): List<DataPoint> {
        val random = Random()
        return (0..20).map {
            DataPoint(it, random.nextInt(20) + 1)
        }
    }

    private fun setTransition(){
        with(window){
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            val slide =  Slide(Gravity.RIGHT)
            slide.duration = 200
            enterTransition = slide
        }
    }
}