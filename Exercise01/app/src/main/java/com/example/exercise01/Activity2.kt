package com.example.exercise01

import android.annotation.SuppressLint
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.Switch

class Activity2 : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_2)

        val img1: View = findViewById(R.id.img1)

        val swListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                img1.visibility = View.VISIBLE
            else
                img1.visibility = View.INVISIBLE
        }

        val sw: Switch = findViewById(R.id.switch1)
        sw.isChecked = true
        sw.setOnCheckedChangeListener(swListener)
    }
}