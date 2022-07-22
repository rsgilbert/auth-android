package net.passioncloud.auth.accounts

import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import net.passioncloud.auth.R

class RegistrationActivity : AccountAuthenticatorActivity(){
    private val accountName = "Gilbert account"
    private val accountType = resources.getString(R.string.account_type)
    private val authTokenType = "Simple auth token type"

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
//        setContent {
//            AuthTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colors.background
//                ) {
//                    Greeting("Login here")
//                }
//            }
//        }
    }

    fun register(accountName: String, password: String, requiredFeatures: Array<String>, options: Bundle) {
        Toast.makeText(this,"Registering", Toast.LENGTH_LONG).show()
        val account = Account(accountName, accountType)
        val refreshToken = "THE REFRESHER"
        AccountManager.get(this).addAccountExplicitly(account, refreshToken, options)
        val bundle = makeBundle(accountName, accountType, authTokenType, refreshToken, options)
        setAccountAuthenticatorResult(bundle)
        finish()
    }

    private fun makeBundle(accountName: String, accountType: String, authTokenType: String, refreshToken: String, options: Bundle): Bundle {
        val bundle = Bundle()
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, accountName)
        bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType)
        bundle.putString(AccountManager.KEY_PASSWORD, refreshToken)
        return bundle
    }
}