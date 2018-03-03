package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.Account
import com.astimefades.beatsyncandroid.model.config.ApplicationConfiguration
import com.astimefades.beatsyncandroid.model.request.LoginAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.web.PersistenceApi
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class LoginActivity : AppCompatActivity() {

    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(userIsLoggedIn()) {
            handleUserAlreadyLoggedIn()
        } else {
            loginSubmit.setOnClickListener { handleLogin() }
            loginSignUp.setOnClickListener { handleSignUp() }
        }
    }

    private fun sendLoginRequest(email: String, password: String, success: (Account) -> Unit, failure: (errorDescription: String) -> Unit) {
        doAsync {
            val loginAccountRequest = Request(LoginAccountRequest(email, password))
            val response = persistenceApi.loginAccount(loginAccountRequest).execute().body()
            uiThread {
                if (response != null) {
                    if(response.errorDescription != null) {
                        failure(response.errorDescription)
                    } else {
                        success(response.payload!!)
                    }
                } else {
                    handleNetworkTimeout()
                }
            }
        }
    }

    private fun handleLogin() {
        sendLoginRequest(
                loginEmail.text.toString(),
                loginPassword.text.toString(),
                { account -> handleSuccessfulLoginResponse(account.id, loginPassword.text.toString(), loginEmail.text.toString()) },
                { errorDescription -> handleLoginResponseError(errorDescription) }
        )
    }

    private fun handleSignUp() {
        startActivity<SignUpActivity>()
    }

    private fun handleUserAlreadyLoggedIn() {
        val accountPrefs = ApplicationConfiguration.getInstance(ApplicationConfiguration.ACCOUNT_PREF_FILE, this@LoginActivity)
        val email = accountPrefs.getString(ApplicationConfiguration.ACCOUNT_EMAIL_PROP, null)
        val password = accountPrefs.getString(ApplicationConfiguration.ACCOUNT_PASSWORD_PROP, null)
        sendLoginRequest(
                email,
                password,
                { account -> handleSuccessfulLoginResponse(account.id, password, email) },
                { _ -> handleLoginResponseError("Account session ended. Please login again.") }
        )

        startActivity<MainActivity>()
    }

    private fun handleLoginResponseError(errorDescription: String) {
        loginPassword.setText("")
        Toast.makeText(this@LoginActivity, errorDescription, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccessfulLoginResponse(accountId: String, password: String, email: String) {
        val accountPrefs = ApplicationConfiguration.getInstance(ApplicationConfiguration.ACCOUNT_PREF_FILE, this@LoginActivity).edit()
        accountPrefs.putString(ApplicationConfiguration.ACCOUNT_ID_PROP, accountId)
        //TODO: Create backend session based authentication and remove storing passwords on device
        accountPrefs.putString(ApplicationConfiguration.ACCOUNT_PASSWORD_PROP, password)
        accountPrefs.putString(ApplicationConfiguration.ACCOUNT_EMAIL_PROP, email)
        accountPrefs.apply()

        startActivity<MainActivity>()
    }

    private fun handleNetworkTimeout() {
        handleLoginResponseError("Unexpected error! Please try again.")
    }

    private fun userIsLoggedIn(): Boolean {
        val accountPrefs = ApplicationConfiguration.getInstance(ApplicationConfiguration.ACCOUNT_PREF_FILE, this@LoginActivity)
        return accountPrefs.getString(ApplicationConfiguration.ACCOUNT_ID_PROP, null) != null
    }
}

