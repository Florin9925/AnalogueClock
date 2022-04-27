package com.example.analogueclock.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.example.analogueclock.R
import com.example.analogueclock.databinding.ActivityMainBinding
import com.example.analogueclock.utility.MySurfaceView

class MainActivity : Activity() {
    private lateinit var binding: ActivityMainBinding
    var mySurfaceView: MySurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.alarms.setOnClickListener {
            val myIntent = Intent(it.context, AlarmActivity::class.java)
            startActivity(myIntent)
        }
        binding.settings.setOnClickListener {
            val myIntent = Intent(it.context, SettingsActivity::class.java)
            startActivity(myIntent)
        }
        mySurfaceView = MySurfaceView(this, 300F)
        val linearLayout = findViewById<View>(R.id.surfaceView) as LinearLayout
        linearLayout.addView(mySurfaceView)
    }

    override fun onResume() {
        super.onResume()
        mySurfaceView?.onResumeMySurfaceView()
    }

    override fun onPause() {
        super.onPause()
        mySurfaceView?.onPauseMySurfaceView()
    }
}