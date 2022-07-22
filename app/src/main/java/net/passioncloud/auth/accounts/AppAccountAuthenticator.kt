package net.passioncloud.auth.accounts

import android.accounts.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import net.passioncloud.auth.MainActivity

// See: http://www.digigene.com/android/accounts-in-android-part-two/
class AppAccountAuthenticator(private val context: Context) :
    AbstractAccountAuthenticator(context) {
    private val tag = javaClass.simpleName

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle {
        // We open the app from its launcher screen
        // We do not try to go straight to the login page
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val result = Bundle()
        result.putParcelable(AccountManager.KEY_INTENT, intent)
        return result
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
        Log.d(tag, "Getting auth token for account name ${account.name} and auth token type $authTokenType")
        val result = Bundle()
        Thread.sleep(5000)
        val accountManager = AccountManager.get(context)
        result.putString(
            AccountManager.KEY_AUTHTOKEN,
            accountManager.peekAuthToken(account, authTokenType)
        )
        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
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

}
