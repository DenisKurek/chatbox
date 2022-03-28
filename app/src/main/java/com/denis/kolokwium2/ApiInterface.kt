package com.denis.kolokwium2

import retrofit2.Call
import retrofit2.http.*
//interfejst obsługujący zapytania http
interface ApiInterface {
    @GET("/shoutbox/messages")
    fun getData(): Call<List<MyDataItem>>
    @POST("/shoutbox/message")
    fun createPost(@Body postDataItem:PostDataItem ): Call<PostDataItem>
    @PUT("/shoutbox/message/{id}")
    fun putPost(@Path("id") id:String ,@Body postDataItem:PostDataItem ): Call<PostDataItem>
    @DELETE("/shoutbox/message/{id}")
    fun deletePost(@Path("id") id:String): Call<Unit>

}