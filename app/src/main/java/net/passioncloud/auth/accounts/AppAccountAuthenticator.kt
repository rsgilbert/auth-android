package net.passioncloud.auth.accounts

import android.accounts.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

// See: http://www.digigene.com/android/accounts-in-android-part-two/
class AppAccountAuthenticator(private val context: Context) :
    AbstractAccountAuthenticator(context) {
    val tag = javaClass.simpleName
    private val addAccountActivityClass = LoginActivity::class.java

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        Log.d(tag, "Adding account from authenticator")
        val intent = makeIntent(response, accountType, authTokenType, requiredFeatures, options)
        return makeBundle(intent)
    }

    override fun confirmCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account,
        authTokenType: String?,
        options: Bundle?
    ): Bundle {
        Log.d(tag, "Getting auth token for account name ${account.name}")
        Thread.sleep(5000)
        Log.d(tag,"Woken up from sleep")
        val result = Bundle()
        val accountManager = AccountManager.get(context)
        val refreshToken = accountManager.getPassword(account)
        if(refreshToken != null)
         result.putString(AccountManager.KEY_AUTHTOKEN, "OLD_AUTH_TKN_PIE")

        result.putString(AccountManager.KEY_PASSWORD, refreshToken)
        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)

        Log.d(tag, "auth token bundle $result, RefreshToken $refreshToken")
        return result
    }

    override fun getAuthTokenLabel(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun updateCredentials(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: String?,
        p3: Bundle?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun hasFeatures(
        p0: AccountAuthenticatorResponse?,
        p1: Account?,
        p2: Array<out String>?
    ): Bundle {
        TODO("Not yet implemented")
    }

    override fun editProperties(p0: AccountAuthenticatorResponse?, p1: String?): Bundle {
        TODO("Not yet implemented")
    }

    private fun makeIntent(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Intent {
        val result = Intent(context, addAccountActivityClass)
        result.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType)
        result.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        return result
    }

    private fun makeBundle(intent: Intent): Bundle {
        val result = Bundle()
        result.putParcelable(AccountManager.KEY_INTENT, intent)
        return result
    }
}
//
//class AuthenticatorManager {
//    fun getAccessTokenCallback(authTokenType: String, options: Bundle, account: Account): AccountManagerCallback<Bundle> {
//        return AccountManagerCallback<Bundle> {  future ->
//            try {
//                val bundle = future.result
//                val accountName = bundle.getString(AccountManager.KEY_ACCOUNT_NAME, null)
//                val refreshToken = bundle.getString(AccountManager.KEY_PASSWORD, null)
//                val authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN, null)
//                if (authToken != null) {
//                    signInAndDoAfter(account, authTokenType, authToken, options)
//                } else {
//                    if (refreshToken != null) {
//                        signUpAndDoAfter(account, authTokenType, refreshToken, options)
//                    } else {
//                        if (accountName != null) {
//                            addAccount(authTokenType, null, options)
//                        } else {
//                            Toast.makeText(context, "Account does not exist", Toast.LENGTH_LONG)
//                                .show()
//                        }
//                    }
//                }
//            }
//            catch(e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
//}