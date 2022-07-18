package net.passioncloud.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class AuthenticationState {
    Authenticated,
    PendingAuthentication,
    NotAuthenticated
}

class MainViewModel : ViewModel() {
    val authenticationStateLiveData: MutableLiveData<AuthenticationState> = MutableLiveData(AuthenticationState.PendingAuthentication)

}