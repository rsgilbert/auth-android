package net.passioncloud.auth

import android.content.Intent
import android.os.Bundle
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
import net.passioncloud.auth.ui.main.MainScreen
import net.passioncloud.auth.ui.theme.AuthTheme
import androidx.compose.runtime.*

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
                    MainScreen()
                }
            }
        }

        val content : View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener {
            // end with false if we are not yet ready
            when(mainViewModel.authenticationStateLiveData.value) {
                AuthenticationState.PendingAuthentication -> true // TODO update
                AuthenticationState.NotAuthenticated -> {
                    goToLoginActivity()
                    finish()
                    true
                }
                AuthenticationState.Authenticated -> true
                null -> false
            }
        }
    }
    private fun goToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
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