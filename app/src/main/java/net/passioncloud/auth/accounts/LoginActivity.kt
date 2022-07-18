package net.passioncloud.auth.accounts

import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import net.passioncloud.auth.Greeting
import net.passioncloud.auth.R
import net.passioncloud.auth.ui.theme.AuthTheme
import kotlin.math.log


class LoginActivity : AccountAuthenticatorActivity() {
    private val accountType = "Auth"
    private val authTokenType = "Simple auth token type"
    private lateinit var loginButton : Button
//    private lateinit var emailText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener { register("James", "pear", arrayOf()) }
    }

    fun register(accountName: String, password: String, requiredFeatures: Array<String>) {
        Toast.makeText(this,"Registering", Toast.LENGTH_LONG).show()
        val account = Account(accountName, accountType)
        val refreshToken = "THE REFRESHER"
        AccountManager.get(this).addAccountExplicitly(account, refreshToken, Bundle())
        val bundle = makeBundle(accountName, accountType, authTokenType, refreshToken)
        setAccountAuthenticatorResult(bundle)
        finish()
    }

    private fun makeBundle(accountName: String, accountType: String, authTokenType: String, refreshToken: String): Bundle {
        val bundle = Bundle()
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, accountName)
        bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType)
        bundle.putString(AccountManager.KEY_PASSWORD, refreshToken)
        return bundle
    }
}

