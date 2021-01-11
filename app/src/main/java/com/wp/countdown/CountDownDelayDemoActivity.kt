package com.wp.countdown

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wp.count_down_timer.GlobalCountDownTimer
import kotlinx.android.synthetic.main.activity_count_down_delay_demo.*

@SuppressLint("SetTextI18n")
class CountDownDelayDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down_delay_demo)
        countDown1()
        countDown2()
    }

    private fun countDown1() {
        var remainTime = 200
        GlobalCountDownTimer.executeCountDownTask(this, Runnable {
            tvCountDown1.text = "倒计时:${remainTime}秒"
            remainTime--
        }, 3)
    }

    private fun countDown2() {
        var remainTime = 7300
        val calculator = MillisecondCalculator()
        GlobalCountDownTimer.executeCountDownTask(this, Runnable {
            calculator.millisecond = remainTime * 1000L
            tvCountDown2.text =
                "剩余${calculator.hourText}小时${calculator.minuteText}分钟${calculator.secondText}秒"
            remainTime--
        }, 8)
    }
}