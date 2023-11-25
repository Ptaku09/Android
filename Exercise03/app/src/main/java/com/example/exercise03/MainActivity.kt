package com.example.exercise03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction

lateinit var fragmentDog: FragmentDog
lateinit var fragmentCat: FragmentCat
lateinit var myTrans: FragmentTransaction

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentDog = FragmentDog.newInstance()
        fragmentCat = FragmentCat.newInstance()
        myTrans = supportFragmentManager.beginTransaction()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}