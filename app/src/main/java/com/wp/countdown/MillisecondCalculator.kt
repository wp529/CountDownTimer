package com.wp.countdown

/**
 * create by WangPing
 * on 2020/7/3
 */
class MillisecondCalculator {
    var millisecond: Long = 0
        set(value) {
            field = value
            calculateToTime()
        }
    var dayText: String = ""
        private set
    var hourText: String = ""
        private set
    var minuteText: String = ""
        private set
    var secondText: String = ""
        private set


    private fun calculateToTime() {
        val perSecond = 1000
        val perMinute = perSecond * 60
        val perHour = perMinute * 60
        val perDay = perHour * 24

        val days = millisecond / perDay
        val hours = (millisecond - days * perDay) / perHour
        val minutes = (millisecond - days * perDay - hours * perHour) / perMinute
        val seconds = (millisecond - days * perDay - hours * perHour - minutes * perMinute) / perSecond

        dayText = if (days == 0L) {
            ""
        } else {
            days.toString()
        }
        hourText = if (hours < 10) {
            "0$hours"
        } else {
            hours.toString()
        }
        minuteText = if (minutes < 10) {
            "0$minutes"
        } else {
            minutes.toString()
        }
        secondText = if (seconds < 10) {
            "0$seconds"
        } else {
            seconds.toString()
        }
    }
}