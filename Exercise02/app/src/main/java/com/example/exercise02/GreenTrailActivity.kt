package com.example.exercise02

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class GreenTrailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_green_trail)
    }

    fun finishActivity(view: View) {
        finish()
    }
}