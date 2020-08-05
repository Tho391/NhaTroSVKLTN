package com.thomas.quickbloxchat.screen.call

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CallViewModel : ViewModel() {
    val timer: LiveData<String>
        get() = _timer

    private var _timer = MutableLiveData<String>().apply { value = "00:00" }

    private val maxCounter = 24 * 60 * 60 * 1000
    private val counterInterVal = 1 * 1000

    private val countDownTimer = object : CountDownTimer(
        maxCounter.toLong(),
        counterInterVal.toLong()
    ) {
        override fun onFinish() {

        }

        override fun onTick(millisUntilFinished: Long) {
            val diff = (maxCounter - millisUntilFinished) / 1000
            var second = (diff % 1000 % 60).toString()
            var minute = (diff % 1000 / 60).toString()

            if (second.length == 1) second = "0$second"
            if (minute.length == 1) minute = "0$minute"

            _timer.postValue("$minute:$second")
        }

    }

    fun startTimer() {
        countDownTimer.start()
    }

    fun stopTimer() {
        countDownTimer.cancel()
        _timer.postValue("00:00")
    }

}