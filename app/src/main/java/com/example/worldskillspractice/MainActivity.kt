package com.example.worldskillspractice

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.Window
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.worldskillspractice.databinding.ActivityMainBinding
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import java.security.Permissions
import java.util.Calendar
import java.util.jar.Manifest
@SuppressLint("ClickableViewAccessibility")
class MainActivity : AppCompatActivity()  {
    private lateinit var locationRequest: CurrentLocationRequest
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    lateinit var permissions: Array<String>
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var locationUpdate : Location
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLauncher()
        permissions = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest
            .permission.ACCESS_FINE_LOCATION)
        if(permissions.all {
            hasPermission(it)
            }){
                setUpLocationSettings()
                getLocation()
        }else{
            requestPermissions(permissions)
        }
        binding.button.setOnClickListener {
            getLocation()
        }

        binding.calendarTv.setOnClickListener {
            val datePicker = DatePickerDialog(this)
            datePicker.setOnDateSetListener { view, year, month, day ->

                val date = "$year/${String.format("%02d",month+1)}/${String.format("%02d",day)}"
                binding.calendarTv.text = date
            }
            val calendar = Calendar.getInstance()
            datePicker.updateDate(calendar[Calendar.YEAR],calendar[Calendar.MONTH],calendar[Calendar
                .DAY_OF_MONTH])
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()
        }

        val image = assets.open("myfriends.jpg")
        val bitmap = BitmapFactory.decodeStream(image)
        val nBit = Bitmap.createScaledBitmap(bitmap,24,24,false)
        binding.parent.setOnTouchListener(object:MyGesture(this){
            override fun swipeLeft() {
                val trans = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity).toBundle()
                startActivity(Intent(this@MainActivity,MapActivity::class.java),trans)
                Toast.makeText(this@MainActivity, "Swipe Left", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setUpLocationSettings(){
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)
        locationRequest = CurrentLocationRequest.Builder().setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()
    }
    @SuppressLint("MissingPermission")
    fun getLocation(){

        val location = fusedLocationProvider.getCurrentLocation(locationRequest,null)

        location.addOnCompleteListener {
            Toast.makeText(this, "New location ${it.result.latitude}", Toast.LENGTH_SHORT).show()
            val loc = "${it.result.longitude} , ${it.result.latitude}"
            binding.locationTv.text = loc
        }

    }

    private fun hasPermission(permission:String) = ContextCompat.checkSelfPermission(this,permission) ==
            PackageManager.PERMISSION_GRANTED

    private fun requestPermissions(permissions: Array<String>){
        permissionLauncher.launch(permissions)
    }
    private fun setupLauncher(){
        permissionLauncher = registerForActivityResult(ActivityResultContracts
            .RequestMultiplePermissions()){
            grantResults->
            if(grantResults.all{
                hasPermission(it.key) })
            {
                //Granted
                setUpLocationSettings()
                getLocation()
            }else{
                if(permissions.any{
                    needRationale(it)
                    })
                {
                    AlertDialog.Builder(this)
                        .setTitle("Need Permissions")
                        .setPositiveButton("Ok"){
                            _,_->
                            requestPermissions(permissions)
                        }
                }
            }
        }
    }
    private fun needRationale(permission:String) = shouldShowRequestPermissionRationale(permission)
}