package com.wp.count_down_timer

internal data class Task(
    var delayTime: Int,
    val runnable: Runnable,
    var needRemove: Boolean
)