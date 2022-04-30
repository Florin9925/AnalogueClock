package com.example.analogueclock.activity


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.analogueclock.databinding.ActivityMainBinding
import com.example.analogueclock.utility.MySurfaceView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mySurfaceView: MySurfaceView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Analogue Clock"

        binding.alarms.setOnClickListener {
            val myIntent = Intent(it.context, AlarmActivity::class.java)
            startActivity(myIntent)
        }
        binding.settings.setOnClickListener {
            val myIntent = Intent(it.context, SettingsActivity::class.java)
            startActivity(myIntent)
        }
        mySurfaceView = MySurfaceView(this, 300F)

        binding.clockView.addView(mySurfaceView)

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