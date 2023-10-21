package com.example.exercise01

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val myListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.button_araujo -> {
                val myIntent = Intent(this, ActivityAraujo::class.java)
                startActivity(myIntent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLewandowski: Button = findViewById(R.id.button_lewandowski)
        buttonLewandowski.setOnClickListener {
            val myIntent = Intent(this, ActivityLewandowski::class.java)
            startActivity(myIntent)
        }

        val buttonPedri: Button = findViewById(R.id.button_pedri)
        buttonPedri.setOnClickListener(this)

        val buttonAraujo: Button = findViewById(R.id.button_araujo)
        buttonAraujo.setOnClickListener(myListener)
    }

    override fun onClick(p0: View?) {
        val myIntent = Intent(this, ActivityPedri::class.java)
        startActivity(myIntent)
    }

    fun runGavi(view: View) {
        val myIntent = Intent(this, ActivityGavi::class.java)
        startActivity(myIntent)
    }
}