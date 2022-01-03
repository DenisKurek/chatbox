package com.denis.kolokwium2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {
    @GET("/shoutbox/messages")
    fun getData(): Call<List<MyDataItem>>
    @POST("/shoutbox/message")
    fun createPost(@Body postDataItem:PostDataItem ): Call<PostDataItem>
}