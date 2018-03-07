package com.astimefades.beatsyncandroid

import kotlinx.android.synthetic.main.content_sign_up.*

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.web.PersistenceApi
import org.jetbrains.anko.startActivity

class SignUpActivity : AppCompatActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@SignUpActivity) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpSubmit.setOnClickListener { handleSignUp() }

        val password = signUpPassword.text.toString().trim()
        val confirmPassword = signUpConfirmPassword.text.toString().trim()
        if(password != confirmPassword) {
            handleMismatchingPasswords()
        }
    }

    private fun handleSignUp() {
        val email = signUpEmail.text.toString()
        val password = signUpPassword.text.toString()
        PersistenceApi.send(
            Request(CreateAccountRequest(email, password)),
            PersistenceApi::createAccount,
            { proxyId: String -> handleSuccessfulSignUp(proxyId) },
            { errorDescription, _ -> handleSignUpError(errorDescription) },
            this@SignUpActivity
        )
    }

    private fun handleMismatchingPasswords() {
        signUpConfirmPassword.setText("")
        Toast.makeText(this@SignUpActivity, "Passwords do not match, please try again", Toast.LENGTH_LONG).show()
    }

    private fun handleSignUpError(errorDescription: String) {
        Toast.makeText(this@SignUpActivity, errorDescription, Toast.LENGTH_LONG).show()
    }

    private fun handleSuccessfulSignUp(proxyId: String) {
        accountConfiguration.updateAccountInformation(proxyId)

        startActivity<MainActivity>()
    }
}
