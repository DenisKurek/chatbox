package com.denis.chatbox

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class EdtidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        val intent: Intent = getIntent()
        loginView.setText(intent.getStringExtra("P_login"))
        dateView.setText(intent.getStringExtra("date"))
        contentView.setText(intent.getStringExtra("content"))
        contentView.setOnEditorActionListener() { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEND){
                putPost()
                true
            } else {
                false
            }
        }
    }

    private fun putPost() {
        if(checkInternetConnection()==true) {
            val intent: Intent = getIntent()
            val sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE)
            val login = sharedPref.getString("login", null)
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://tgryl.pl")
                .build()
                .create(ApiInterface::class.java)
            val post = PostDataItem(contentView.text.toString(), login)
            retrofitBuilder.putPost(intent.getStringExtra("id").toString(), post)
                .enqueue(object : Callback<PostDataItem> {
                    override fun onResponse(
                        call: Call<PostDataItem>,
                        response: Response<PostDataItem>
                    ) {
                        if (response.isSuccessful) {
                            changeActivity()
                        } else {
                            contentView.setText(intent.getStringExtra("id"))
                        }
                    }

                    override fun onFailure(call: Call<PostDataItem>, t: Throwable) {
                        dateView.setText(t.message)
                        Log.d("MainAc", "onFailure: " + t.message);
                    }
                })
        }
        else{
            Toast.makeText(applicationContext,"you are offline", Toast.LENGTH_SHORT).show()
        }
    }
    private fun changeActivity() {
        val intent = Intent (this,MainActivity::class.java)
        startActivity(intent)
    }

    fun deletePost(view: View) {
        if(checkInternetConnection()==true) {
            val retrofitBuilder = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://tgryl.pl")
                .build()
                .create(ApiInterface::class.java)
            retrofitBuilder.deletePost(intent.getStringExtra("id").toString())
                .enqueue(object : Callback<Unit> {

                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            changeActivity()
                        } else {
                            contentView.setText(intent.getStringExtra("id"))
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        dateView.setText(t.message)
                        Log.d("MainAc", "onFailure: " + t.message);
                    }
                })
        }
        else{
            Toast.makeText(applicationContext,"you are offline", Toast.LENGTH_SHORT).show()
        }
    }
    private fun checkInternetConnection():Boolean {
        var connected:Boolean  = false;
        val connectivityManager: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager;
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.getState() == NetworkInfo.State.CONNECTED ||
            connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.getState() == NetworkInfo.State.CONNECTED) {
            connected=true;
        }
        else
            connected = false;
        return connected;
    }
}