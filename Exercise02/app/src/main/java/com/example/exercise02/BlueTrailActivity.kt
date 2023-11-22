package com.example.exercise02

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class BlueTrailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTheme()
        setContentView(R.layout.activity_blue_trail)
    }

    fun finishActivity(view: View) {
        finish()
    }

    private fun applyTheme() {
        val data = getSharedPreferences("theme", MODE_PRIVATE)

        when (data.getInt("theme_num", 0)) {
            0 -> {
                setTheme(R.style.Theme_Exercise02_BlueTrail)
            }

            1 -> {
                setTheme(R.style.Theme_Exercise02_BlueTrailDark)
            }
        }
    }
}