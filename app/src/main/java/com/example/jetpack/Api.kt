package com.example.jetpack

import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Api {
    fun getInstance():ApiInterface{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mocki.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiInterface::class.java)
    }

    suspend fun getUsers():List<User> = withContext(Dispatchers.IO){
        return@withContext getInstance().getUsers()
    }
}