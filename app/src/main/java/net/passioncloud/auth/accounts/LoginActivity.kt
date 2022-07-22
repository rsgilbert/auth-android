package net.passioncloud.auth.accounts

import android.accounts.Account
import android.accounts.AccountAuthenticatorActivity
import android.accounts.AccountManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.passioncloud.auth.Greeting
import net.passioncloud.auth.R
import net.passioncloud.auth.ui.theme.AuthTheme
import kotlin.math.log


class LoginActivity : ComponentActivity() {
    private val accountType = "Auth"
    private val authTokenType = "Simple auth token type"
    private lateinit var loginButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthTheme {
                LoginScreen()
            }
        }
    }

    fun register(accountName: String, password: String, requiredFeatures: Array<String>) {
        Toast.makeText(this,"Registering", Toast.LENGTH_LONG).show()
        val account = Account(accountName, accountType)
        val refreshToken = "THE REFRESHER"
        val accountManager = AccountManager.get(this)
        AccountManager.get(this).addAccountExplicitly(account, refreshToken, Bundle())
        accountManager.setAuthToken(account, accountType, "Kk")
//        val bundle = makeBundle(accountName, accountType, authTokenType, refreshToken)
        finish()
    }
}

@Composable
fun LoginScreen() {
//    val loginViewModel : LoginViewModel = viewModel()
//    val email =
//    Column {
//        TextField(value = , onValueChange = )
//    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    AuthTheme {
        LoginScreen()
    }
}