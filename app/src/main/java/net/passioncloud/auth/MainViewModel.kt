package net.passioncloud.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

enum class AuthenticationState {
    Authenticated,
    PendingAuthentication,
    NotAuthenticated
}

data class MainUiState(
    var authenticationState: AuthenticationState = AuthenticationState.PendingAuthentication,
    var authToken: String = ""
)

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    private val tag = "MainVM"
    val authenticationStateLiveData: MutableLiveData<AuthenticationState> =
        MutableLiveData(AuthenticationState.PendingAuthentication)
    val authTokenLiveData: MutableLiveData<String> = MutableLiveData("Coming up")
    private val accountManager = AccountManager.get(app.applicationContext)
    val authTokenType = "Simple auth token type"
    private var authToken: String = ""
    val account = Account("Peter", "Auth")
    init {
       val f = accountManager.getAuthToken(account, authTokenType, null,true, { future ->
            val bundle = future.result
            authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN, "")
            Log.d(tag, "Auth token is $authToken")
            if (TextUtils.isEmpty(authToken)) {
                Log.d(tag, "Auth token was blank")
                authenticationStateLiveData.value = AuthenticationState.Authenticated
               } else {
                authenticationStateLiveData.value = AuthenticationState.Authenticated
               }
           authTokenLiveData.value = "XYZ"
        }, null)
    }
}
