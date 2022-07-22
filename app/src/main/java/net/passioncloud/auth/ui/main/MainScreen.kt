package net.passioncloud.auth.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import net.passioncloud.auth.AuthenticationState
import net.passioncloud.auth.MainViewModel

@Composable
fun MainScreen(mainViewModel: MainViewModel = viewModel()) {
    val authenticationState by mainViewModel.authenticationStateLiveData.observeAsState(AuthenticationState.PendingAuthentication)
    val authToken by mainViewModel.authTokenLiveData.observeAsState("N")
    Column {
        Text("Main screen")
        Text(authenticationState.name)
        Text("Authorization/Token")
        Text(authToken)

    }
}