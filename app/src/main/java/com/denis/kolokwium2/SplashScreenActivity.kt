package com.denis.kolokwium2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreenActivity : AppCompatActivity() {
    lateinit var handler: Handler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        handler = Handler();
        handler.postDelayed({
            val sharedPref =  getSharedPreferences("login", Context.MODE_PRIVATE)
            val login= sharedPref.getString("login",null)
            var intent=Intent()
            if(login==null) {
                intent = Intent(this, LoginActivity::class.java)
            }
            else{
                intent = Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
            finish()
        },2000)
    }
}