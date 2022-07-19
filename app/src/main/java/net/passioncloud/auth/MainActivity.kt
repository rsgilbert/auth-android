package net.passioncloud.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.OnAccountsUpdateListener
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import net.passioncloud.auth.accounts.LoginActivity
import net.passioncloud.auth.ui.theme.AuthTheme

class MainActivity : ComponentActivity() {
    val mainViewModel : MainViewModel by viewModels()
    private val tag = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            AuthTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }

        val content : View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener {
            // end with false if we are not yet ready
            when(mainViewModel.authenticationStateLiveData.value) {
                AuthenticationState.PendingAuthentication -> false
                AuthenticationState.NotAuthenticated -> {
                    Log.d(tag, "Not authenticated, adding account")
                    val accountManager = AccountManager.get(this)
                    accountManager.addAccount(
                        mainViewModel.account.type,
                        mainViewModel.authTokenType,
                        emptyArray(),
                        null,
                        this,
                        { future ->
                            val bundle = future.result
                            Log.d(tag, "Future result bundle for adding account is $bundle")
                            restartActivity()
                        },
                        null
                    )
                    true
                }
                AuthenticationState.Authenticated -> true
                null -> false
            }
        }
    }
    private fun restartActivity() {
        val intent = Intent(this, this::class.java)
        startActivity(intent)
        finish()
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AuthTheme {
        Greeting("Android")
    }
}