package com.astimefades.beatsyncandroid

import kotlinx.android.synthetic.main.content_sign_up.*

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.config.ApplicationConfiguration
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.web.PersistenceApi
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

class SignUpActivity : AppCompatActivity() {

    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val password = signUpPassword.text.toString().trim()
        val confirmPassword = signUpConfirmPassword.text.toString().trim()
        if(password != confirmPassword) {
            handleMismatchingPasswords()
        } else {
            signUpSubmit.setOnClickListener { handleSignUp() }
        }
    }

    private fun handleSignUp() {
        doAsync {
            val createAccountRequest = Request(CreateAccountRequest(signUpEmail.text.toString(), signUpPassword.text.toString()))
            val response = persistenceApi.createAccount(createAccountRequest).execute().body()
            uiThread {
                if(response != null) {
                    if(response.errorDescription != null) {
                        handleSignUpError(response.errorDescription)
                    } else {
                        handleSuccessfulSignUp(response.payload!!.id, signUpPassword.text.toString(), signUpEmail.text.toString())
                    }
                } else {
                    handleNetworkTimeout()
                }
            }
        }
    }

    private fun handleMismatchingPasswords() {
        signUpConfirmPassword.setText("")
        Toast.makeText(this@SignUpActivity, "Passwords do not match, please try again", Toast.LENGTH_LONG).show()
    }

    private fun handleSignUpError(errorDescription: String) {
        Toast.makeText(this@SignUpActivity, errorDescription, Toast.LENGTH_LONG).show()
    }

    private fun handleNetworkTimeout() {
        handleSignUpError("Unexpected error! Please try again.")
    }

    private fun handleSuccessfulSignUp(accountId: String, password: String, email: String) {
        val accountPrefs = ApplicationConfiguration.getInstance(ApplicationConfiguration.ACCOUNT_PREF_FILE, this@SignUpActivity).edit()
        accountPrefs.putString(ApplicationConfiguration.ACCOUNT_ID_PROP, accountId)
        accountPrefs.putString(ApplicationConfiguration.ACCOUNT_PASSWORD_PROP, password)
        accountPrefs.putString(ApplicationConfiguration.ACCOUNT_EMAIL_PROP, email)
        accountPrefs.apply()

        startActivity<MainActivity>()
    }
}
