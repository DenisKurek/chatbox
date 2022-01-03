package com.denis.kolokwium2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.core.view.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.raw_items.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(),MyAdapter.OnItemclickListener {
    lateinit var myAdapter: MyAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    val swipeHelper = object:SwipeHelper(){
        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            myAdapter.deleteItem(viewHolder.adapterPosition)
        }
    }
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
        val touchHelper= ItemTouchHelper(swipeHelper)
        touchHelper.attachToRecyclerView(recyclerview_users)
        getMyData()
        swiperefreshlayout.setOnRefreshListener(){
            getMyData()
            swiperefreshlayout.isRefreshing=false
        }
        val t1 = Thread(
            Runnable {
                Thread.sleep(60000)
                getMyData()
            }
        )
        t1.start()
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
                myAdapter= MyAdapter(baseContext, responseBody as ArrayList<MyDataItem>,this@MainActivity)
                myAdapter.notifyDataSetChanged()
                recyclerview_users.adapter=myAdapter
            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {
                Log.d("MainAc","onFailure: " +t.message);
            }
        })

    }

    override fun onItemClick(login:String,date:String,id:String,content: String) {
        val sharedPref =  getSharedPreferences("login", Context.MODE_PRIVATE)
        val aktualny= sharedPref.getString("login",null)
        if(login==aktualny) {
            val intent = Intent(this, MainActivity3::class.java)
            intent.putExtra("P_login", login)
            intent.putExtra("id", id)
            intent.putExtra("content", content)
            intent.putExtra("date", date)
            startActivity(intent)
        }
        else{
            Toast.makeText(applicationContext,"you can't edit this post ",Toast.LENGTH_SHORT).show()
        }
    }

}


