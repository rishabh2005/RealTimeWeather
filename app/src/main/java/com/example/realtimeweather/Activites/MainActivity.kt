package com.example.realtimeweather.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.realtimeweather.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button=findViewById<Button>(R.id.Startbutton)

        button.setOnClickListener{
            val intent = Intent(this, Home_Screen::class.java)
            startActivity(intent)
            finish()
        }

    }
}