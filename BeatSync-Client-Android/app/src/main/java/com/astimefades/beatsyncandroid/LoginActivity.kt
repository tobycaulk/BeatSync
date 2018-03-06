package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.config.ApplicationConfiguration
import com.astimefades.beatsyncandroid.model.request.LoginAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.web.PersistenceApi
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginSubmit.setOnClickListener { handleLogin() }
        loginSignUp.setOnClickListener { handleSignUp() }

        if(userIsLoggedIn()) {
            handleUserAlreadyLoggedIn()
        }
    }

    private fun handleLogin() {
        val email = loginEmail.text.toString()
        val password = loginPassword.text.toString()

        PersistenceApi.send(
                Request(LoginAccountRequest(email, password)),
                PersistenceApi::loginAccount,
                { account -> handleSuccessfulLoginResponse(account.id, password, email) },
                { errorDescription, _ -> handleLoginResponseError(errorDescription)},
                { handleNetworkTimeout() },
                this@LoginActivity
        )
    }

    private fun handleSignUp() {
        startActivity<SignUpActivity>()
    }

    private fun handleUserAlreadyLoggedIn() {
        val accountPrefs = ApplicationConfiguration.getInstance(ApplicationConfiguration.ACCOUNT_PREF_FILE, this@LoginActivity)
        val email = accountPrefs.getString(ApplicationConfiguration.ACCOUNT_EMAIL_PROP, null)
        val password = accountPrefs.getString(ApplicationConfiguration.ACCOUNT_PASSWORD_PROP, null)

        PersistenceApi.send(
                Request(LoginAccountRequest(email, password)),
                PersistenceApi::loginAccount,
                { account -> handleSuccessfulLoginResponse(account.id, password, email) },
                { errorDescription, _ -> handleLoginResponseError(errorDescription)},
                { handleNetworkTimeout() },
                this@LoginActivity
        )
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
        loginPassword.setText("")
    }

    private fun userIsLoggedIn(): Boolean {
        val accountPrefs = ApplicationConfiguration.getInstance(ApplicationConfiguration.ACCOUNT_PREF_FILE, this@LoginActivity)
        return accountPrefs.getString(ApplicationConfiguration.ACCOUNT_ID_PROP, null) != null
    }
}

