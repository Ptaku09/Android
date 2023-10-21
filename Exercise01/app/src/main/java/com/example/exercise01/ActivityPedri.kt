package com.example.exercise01

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast

class ActivityPedri : Activity(), View.OnLongClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pedri)

        val act: View = findViewById(R.id.activity_pedri)
        act.setOnLongClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        val toast: Toast = Toast.makeText(this, "Activity Pedri is started", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    override fun onLongClick(view: View?): Boolean {
        onBackPressed()
        return true
    }

    fun finishActivityPedri(view: View) {
        finish()
    }
}