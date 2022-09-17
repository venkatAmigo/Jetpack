package com.example.jetpack

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainViewModel: ViewModel(){

    private val users: MutableLiveData<List<User>> = MutableLiveData()

    val liveUsers :LiveData<List<User>>
    get() = users

    var welcomeText = "Great"

    init {
        getUsers()
    }
    fun getUsers(){
        viewModelScope.launch {
            users.postValue(Api.getUsers())
        }
    }
    fun shuffleData(){
        val lists =  users.value as MutableList<User>
        lists.shuffle(Random(System.currentTimeMillis()))
        users.postValue(lists)
    }
    fun changeUsers(){
        viewModelScope.launch {
            val newUsers = mutableListOf<User>()
            for(i in 0..4){
                newUsers.add(User("Venkat $i"))
            }
            users.value = newUsers
        }
    }
}