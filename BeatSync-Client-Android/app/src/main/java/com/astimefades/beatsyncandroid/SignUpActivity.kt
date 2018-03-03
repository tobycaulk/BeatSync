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
    private val applicationConfiguration = ApplicationConfiguration()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val password = signUpPassword.text.toString().trim()
        val confirmPassword = signUpConfirmPassword.text.toString().trim()
        if(password != confirmPassword) {
            signUpConfirmPassword.setText("")
            Toast.makeText(this@SignUpActivity, "Passwords do not match, please try again", Toast.LENGTH_LONG).show()
        }

        signUpSubmit.setOnClickListener {
            doAsync {
                val createAccountRequest = Request(CreateAccountRequest(signUpEmail.text.toString(), signUpPassword.text.toString()))
                val response = persistenceApi.createAccount(createAccountRequest).execute().body()
                if(response?.errorDescription != null) {
                    uiThread {
                        Toast.makeText(this@SignUpActivity, response.errorDescription, Toast.LENGTH_LONG).show()
                    }
                } else {
                    uiThread {
                        val accountPrefs = applicationConfiguration.getInstance(applicationConfiguration.ACCOUNT_PREF_FILE, this@SignUpActivity)
                        accountPrefs.putString(applicationConfiguration.ACCOUNT_ID_PROP, response?.payload?.id)
                        startActivity<MainActivity>()
                    }
                }
            }
        }
    }
}
