package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.model.request.LoginAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.web.PersistenceApi
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@LoginActivity) }

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
                { proxyId: String -> handleSuccessfulLoginResponse(proxyId) },
                { errorDescription, _ -> handleLoginResponseError(errorDescription)},
                { handleNetworkTimeout() },
                this@LoginActivity
        )
    }

    private fun handleSignUp() {
        startActivity<SignUpActivity>()
    }

    private fun handleUserAlreadyLoggedIn() {
        PersistenceApi.send(
                Request(accountConfiguration.getString(AccountConfiguration.ACCOUNT_PROXY_ID_PROP)),
                PersistenceApi::checkAccountLogin,
                { proxyId: String -> handleSuccessfulLoginResponse(proxyId) },
                { errorDescription, _ -> handleLoginResponseError(errorDescription)},
                { handleNetworkTimeout() },
                this@LoginActivity
        )
    }

    private fun handleLoginResponseError(errorDescription: String) {
        loginPassword.setText("")
        Toast.makeText(this@LoginActivity, errorDescription, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccessfulLoginResponse(proxyId: String) {
        accountConfiguration.updateAccountInformation(proxyId)

        startActivity<MainActivity>()
    }

    private fun handleNetworkTimeout() {
        loginPassword.setText("")
    }

    private fun userIsLoggedIn() = accountConfiguration.getString(AccountConfiguration.ACCOUNT_PROXY_ID_PROP) != null
}

