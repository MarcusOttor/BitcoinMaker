package com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.AppTools
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.MyApplication
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.data.Thread
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.managers.CoinsManager
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.managers.PreferencesManager
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.receiver.NoNetworkReceiver
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.inject.AppModule
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.inject.DaggerAppComponent
import com.vicpin.krealmextensions.queryAll
import java.util.*
import javax.inject.Inject

class MiningService : Service() {

    private var timer: Timer = Timer()
    @Inject lateinit var preferencesManager: PreferencesManager
    @Inject lateinit var coinsManager: CoinsManager

    private var remainingTime = 60000 * 180

    companion object {
        var isTimerRunning = false
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .mainModule((application as MyApplication).mainModule)
                .build().inject(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (AppTools.isNetworkAvaliable(applicationContext)) {
            startTimer()
        }
        return START_STICKY
    }

    private fun startTimer() {
        isTimerRunning = true
        timer.schedule(ClaimTextUpdateTimer(), 0, 60000)
    }

    private fun stopService() {
        isTimerRunning = false
        timer.cancel()
        stopSelf()
    }

    inner class ClaimTextUpdateTimer : TimerTask() {
        override fun run() {
            coinsManager.addCoins(Thread().queryAll().sumBy { (it.satoshi * it.speed) })
            remainingTime -= 60000
            if (remainingTime <= 0) {
                preferencesManager.put(PreferencesManager.MINING_PAUSED, true)
                stopService()
            }
            if (!AppTools.isNetworkAvaliable(applicationContext)) {
                sendBroadcast(Intent(applicationContext, NoNetworkReceiver::class.java))
                stopService()
            }
        }
    }

}
