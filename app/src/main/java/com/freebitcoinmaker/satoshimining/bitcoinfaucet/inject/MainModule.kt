package com.freebitcoinmaker.satoshimining.bitcoinfaucet.inject

import android.content.Context
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.R
import com.yandex.metrica.YandexMetricaConfig
import dagger.Module
import dagger.Provides
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

@Module
class MainModule(var context: Context) {

    @Provides
    fun provideAppMetrica() : YandexMetricaConfig.Builder {
        return YandexMetricaConfig
                .newConfigBuilder(context.resources.getString(R.string.metrica))
    }

    @Provides
    fun provideCalligraphy() : CalligraphyConfig {
        return CalligraphyConfig
                .Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .build()
    }

    @Provides
    fun provideContext() : Context {
        return context
    }
}
