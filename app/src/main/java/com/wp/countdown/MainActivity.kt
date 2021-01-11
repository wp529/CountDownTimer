package com.wp.countdown

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvStartCountDown.setOnClickListener {
            startActivity(Intent(this, CountDownDemoActivity::class.java))
        }
        tvStartCountDownDelay.setOnClickListener {
            startActivity(Intent(this, CountDownDelayDemoActivity::class.java))
        }
    }
}