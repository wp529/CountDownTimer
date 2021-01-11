[![](https://jitpack.io/v/wp529/CountDownTimer.svg)](https://jitpack.io/#wp529/CountDownTimer)

##### 每秒轮询一次的定时器，项目只需这一个定时器，统一管理，方便使用，自动关联生命周期

**添加依赖:**

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
dependencies {
	implementation 'com.github.wp529:CountDownTimer:0.0.1'
}
```

###### 使用方式：
开始倒计时
```
private fun countDown() {
        var remainTime = 20
        GlobalCountDownTimer.executeCountDownTask(lifecycleOwner, Runnable {
            tvCountDown1.text = "倒计时:${remainTime}秒"
            remainTime--
        })
    }
```
延时开始倒计时(单位秒)
```
private fun countDown() {
        var remainTime = 20
        GlobalCountDownTimer.executeCountDownTask(lifecycleOwner, Runnable {
            tvCountDown1.text = "倒计时:${remainTime}秒"
            remainTime--
        }, 3)
    }
```
取消倒计时
```
GlobalCountDownTimer.removeCountDownTask(runnable)
```
