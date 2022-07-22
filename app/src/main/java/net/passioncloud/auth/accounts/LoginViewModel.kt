package net.passioncloud.auth.accounts

import android.accounts.AccountManager
import android.accounts.NetworkErrorException
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.Exception


class LoginViewModel(private val app: Application) : AndroidViewModel(app) {
    private val tag = "loginVM"
    val emailLiveData= MutableLiveData("")
    val passwordLiveData = MutableLiveData("")
    private val accountManager = AccountManager.get(app.applicationContext)

    fun login() {
        val email = emailLiveData.value
        val password = passwordLiveData.value
        if(email.isNullOrBlank()) {
            throw Exception("Email is blank")
        }
        if(password.isNullOrBlank()) {
            throw Exception("Password is blank")
        }
        try {
            viewModelScope.launch {
                val token = authWebService.login(LoginBody(email, password))
                Log.d(tag, "Token is $token")
            }
        }
        catch(e: NetworkErrorException) {
            e.printStackTrace()
        }


    }
}