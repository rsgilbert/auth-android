package net.passioncloud.auth.accounts

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AuthenticatorService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        val appAccountAuthenticator = AppAccountAuthenticator(this)
        return appAccountAuthenticator.iBinder
    }
}