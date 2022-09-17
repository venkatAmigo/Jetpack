package com.example.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.DiffUtil
import com.example.jetpack.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    lateinit var users: List<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        supportFragmentManager.beginTransaction().add(
            binding.fragmentContainerView.id,
            ViewModelFragment.newInstance()
        ).commit()

        viewModel = ViewModelProvider(this, MyViewModelFactory())[MainViewModel::class.java]
        binding.viewModel = viewModel
        viewModel.liveUsers.observe(
            this
        ) {
            users = it
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
            val a = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, users)
            binding.listView.adapter = a
        }

        binding.button.setOnClickListener {
            viewModel.changeUsers()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.shuffleData()
            binding.swipeRefresh.isRefreshing = false
        }
    }
}