package com.example.jetpack

import retrofit2.http.GET


interface ApiInterface {
    @GET("2b5edbc4-7518-44cd-adef-d150ae765be6")
    suspend fun getUsers():List<User>
}