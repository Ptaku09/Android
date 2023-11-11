package com.example.exercise02

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class RedTrailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_red_trail)
    }

    fun finishActivity(view: View) {
        finish()
    }
}