package com.example.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.jetpack.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        //setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this,MyViewModelFactory())[MainViewModel::class.java]
        viewModel.getUsers().observe(this
        ) {
            val  a =ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,it)
            binding.listView.adapter = a
        }

        binding.button.setOnClickListener {
            viewModel.changeUsers()
        }
    }
}