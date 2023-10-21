package com.example.exercise01

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class ActivityLewandowski : AppCompatActivity() {
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lewandowski)

        val img: View = findViewById(R.id.img_lewadowski)

        val swListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                img.visibility = View.VISIBLE
            else
                img.visibility = View.INVISIBLE
        }

        val sw: Switch = findViewById(R.id.switch_lewandowski)
        sw.isChecked = true
        sw.setOnCheckedChangeListener(swListener)
    }
}