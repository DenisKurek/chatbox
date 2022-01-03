package com.denis.kolokwium2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref =  getSharedPreferences("login", Context.MODE_PRIVATE)
        val login= sharedPref.getString("login",null)
        if(login==null){
            changeActivity()
        }
        recyclerview_users.setHasFixedSize(true)
        linearLayoutManager= LinearLayoutManager(this)
        recyclerview_users.layoutManager= linearLayoutManager
        getMyData()
        swiperefreshlayout.setOnRefreshListener(){
            getMyData()
            swiperefreshlayout.isRefreshing=false
        }
    }

    fun postMyData(view: View) {
        val sharedPref =  getSharedPreferences("login", Context.MODE_PRIVATE)
        val login= sharedPref.getString("login",null)
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://tgryl.pl")
            .build()
            .create(ApiInterface::class.java)
       val post = PostDataItem(editname.text.toString(), login)
        editname.setText("")
        retrofitBuilder.createPost(post).enqueue(object : Callback<PostDataItem> {
            override fun onResponse(call: Call<PostDataItem>, response: Response<PostDataItem>) {
                if(response.isSuccessful){
                    getMyData()
                }
            }

            override fun onFailure(call: Call<PostDataItem>, t: Throwable) {
                Log.d("MainAc","onFailure: " +t.message);
            }
        })

    }

    private fun changeActivity() {
        val intent = Intent (this,MainActivity2::class.java)
        startActivity(intent)
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://tgryl.pl")
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(call: Call<List<MyDataItem>?>, response: Response<List<MyDataItem>?>) {
                val responseBody = response.body()!!
                myAdapter= MyAdapter(baseContext,responseBody)
                myAdapter.notifyDataSetChanged()
                recyclerview_users.adapter=myAdapter
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainAc","onFailure: " +t.message);
            }
        })

    }

}


