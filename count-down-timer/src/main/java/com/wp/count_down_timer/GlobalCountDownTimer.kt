package com.wp.count_down_timer

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent

/**
 * create by WangPing
 * on 2021/1/11
 */
object GlobalCountDownTimer {
    private val taskMap = HashMap<LifecycleOwner, ArrayList<Task>>()

    //每秒执行一次任务的handler
    private val perSecondExecuteThreadHandler = Handler(Looper.getMainLooper())

    //轮询状态
    private var running = false

    //每秒执行一次的任务
    private val perSecondExecuteRun = object : Runnable {
        override fun run() {
            val runnableExecuteStartTime = System.currentTimeMillis()
            if (taskMap.isEmpty()) {
                //没有任务需要管理了,轮询器就不轮询了
                Log.i(logTag, "检测到没有任务执行,不再轮询")
                running = false
                perSecondExecuteThreadHandler.removeCallbacks(this)
                return
            }
            Log.i(logTag, "执行轮询任务")
            //移除标志位为true的任务
            taskMap.forEach {
                val needRemoveTask = it.value.filter { task -> task.needRemove }
                it.value.removeAll(needRemoveTask)
            }
            //开始让每个任务执行
            taskMap.forEach {
                Log.d(logTag, "${it.key.javaClass.simpleName}下有${it.value.size}个任务在执行")
                it.value.forEach { task ->
                    if (task.delayTime == 0) {
                        //可以执行的任务
                        task.runnable.run()
                    } else {
                        //还需要延时的任务
                        task.delayTime -= 1
                    }
                }
            }
            val runnableExecuteEndTime = System.currentTimeMillis()
            //减去任务执行的时间,才是下一次需要执行的时间,为了让轮询精准点
            val runnableExecuteUsedTime = runnableExecuteEndTime - runnableExecuteStartTime
            Log.i(this@GlobalCountDownTimer.logTag, "执行轮询任务耗时: $runnableExecuteUsedTime")
            perSecondExecuteThreadHandler.postDelayed(this, 1000 - runnableExecuteUsedTime)
        }
    }

    /**
     * 执行倒计时任务
     * @param lifecycleOwner 生命周期组件
     * @param runnable 倒计时任务
     * @param delay 延迟执行时间,单位秒
     */
    @JvmOverloads
    fun executeCountDownTask(lifecycleOwner: LifecycleOwner, runnable: Runnable, delay: Int = 0) {
        if (delay == 0) {
            runnable.run()
        }
        val task = Task(delay, runnable, false)
        if (taskMap.containsKey(lifecycleOwner)) {
            //这个lifecycle组件已经被加入进来了,只需要将任务追加到后面就行
            val runnableList = taskMap[lifecycleOwner]!!
            if (runnableList.contains(task)) {
                //此任务已经被加入过了
                return
            } else {
                runnableList.add(task)
            }
        } else {
            //lifecycle组件还没被加入进来过,加入进来并注册生命周期监听
            taskMap[lifecycleOwner] = arrayListOf(task)
            lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroy() {
                    //被销毁了就移除管理
                    Log.i(this@GlobalCountDownTimer.logTag, "界面销毁,移除任务")
                    taskMap.remove(lifecycleOwner)
                }
            })
        }
        if (!running) {
            perSecondExecuteThreadHandler.post(perSecondExecuteRun)
            running = true
        }
    }

    /**
     * 移除倒计时任务
     * @param runnable 需要移除的倒计时任务
     */
    fun removeCountDownTask(runnable: Runnable) {
        taskMap.forEach {
            val needRemoveTask = it.value.find { task -> runnable == task.runnable }
            if (needRemoveTask != null) {
                needRemoveTask.needRemove = true
                return@forEach
            }
        }
    }
}