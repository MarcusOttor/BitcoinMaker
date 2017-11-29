package com.freebitcoinmaker.satoshimining.bitcoinfaucet.inject

import com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.MyApplication
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.services.ClaimService
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.core.services.MiningService
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.screens.BaseActivity
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.screens.dialogs.LoginDialog
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.screens.dialogs.PromocodeDialog
import com.freebitcoinmaker.satoshimining.bitcoinfaucet.screens.dialogs.SignupDialog
import dagger.Component

@Component(modules = arrayOf(AppModule::class, MainModule::class))
interface AppComponent {

    fun inject(screen: BaseActivity)
    fun inject(app: MyApplication)
    fun inject(dialog: LoginDialog)
    fun inject(dialog: SignupDialog)
    fun inject(dialog: PromocodeDialog)
    fun inject(service: ClaimService)
    fun inject(service: MiningService)
}
