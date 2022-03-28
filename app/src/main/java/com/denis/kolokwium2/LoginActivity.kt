package com.denis.kolokwium2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_main2.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val sharedPref = getSharedPreferences("login", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        buttonSave.setOnClickListener {
            val login = logText.text.toString()
            editor.apply() {
                putString("login", login)
                apply()
            }
            changeActivity()
        }
    }
    private fun changeActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}