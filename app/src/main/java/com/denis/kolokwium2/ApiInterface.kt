package com.denis.kolokwium2

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("/shoutbox/messages")
    fun getData(): Call<List<MyDataItem>>
}