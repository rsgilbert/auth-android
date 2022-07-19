package net.passioncloud.auth

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

enum class AuthenticationState {
    Authenticated,
    PendingAuthentication,
    NotAuthenticated
}

class MainViewModel(private val app: Application) : AndroidViewModel(app) {
    private val tag = javaClass.simpleName
    val authenticationStateLiveData: MutableLiveData<AuthenticationState> =
        MutableLiveData(AuthenticationState.PendingAuthentication)
    private val accountManager = AccountManager.get(app.applicationContext)
    val authTokenType = "Simple auth token type"
    private var authToken: String = ""
    val account = Account("Peter", "Auth")
    init {
        Log.d(tag, "Beginning hunt, for auth token of account $account with account name ${account.name}")
        val f = accountManager.getAuthToken(account, authTokenType, null,true, { future ->
            val bundle = future.result
            val accountName = bundle.getString(AccountManager.KEY_ACCOUNT_NAME, null)
            val refreshToken = bundle.getString(AccountManager.KEY_PASSWORD, null)
            authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN, "")
            Log.d(tag, "Auth token is $authToken and refresh token is $refreshToken and accountName is $accountName")
            if (authToken.isBlank()) {
                Log.d(tag, "Auth token was blank")
                authenticationStateLiveData.value = AuthenticationState.NotAuthenticated
            } else {
                authenticationStateLiveData.value = AuthenticationState.Authenticated
            }
        }, null)
        Log.d(tag, "Finished account manager future $f")

    }
}
