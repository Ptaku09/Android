package com.example.exercise01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val myListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.button4 -> {
                val myIntent = Intent(this, Activity3::class.java)
                startActivity(myIntent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1: Button = findViewById(R.id.button1)
        button1.setOnClickListener {
            val myIntent = Intent(this, Activity2::class.java)
            startActivity(myIntent)
        }

        val button2: Button = findViewById(R.id.button2)
        button2.setOnClickListener(this)

        val button4: Button = findViewById(R.id.button4)
        button4.setOnClickListener(myListener)
    }

    override fun onClick(p0: View?) {
        val myIntent = Intent(this, Activity3::class.java)
        startActivity(myIntent)
    }

    fun run2(view: View) {
        val myIntent = Intent(this, Activity2::class.java)
        startActivity(myIntent)
    }
}