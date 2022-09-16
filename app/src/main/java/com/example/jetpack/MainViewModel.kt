package com.example.jetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val users: MutableLiveData<List<User>> = MutableLiveData()

    val liveUsers :LiveData<List<User>>
    get() = users

    fun getUsers():LiveData<List<User>>{
        viewModelScope.launch {
            users.value = Api.getUsers()
        }
        return liveUsers
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