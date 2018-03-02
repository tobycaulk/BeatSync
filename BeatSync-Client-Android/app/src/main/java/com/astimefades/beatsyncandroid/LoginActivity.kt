package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        loginSubmit.setOnClickListener {
            doAsync {
                val loginAccountRequest = Request(LoginAccountRequest(loginEmail.text.toString(), loginPassword.text.toString()))
                val response = persistenceApi.loginAccount(loginAccountRequest).execute().body()
                if(response?.errorDescription != null) {
                    uiThread {
                        Toast.makeText(this@LoginActivity, response.errorDescription, Toast.LENGTH_LONG).show()
                    }
                } else {
                    uiThread {
                        startActivity<MainActivity>()
                    }
                }
            }
        }

        loginSignUp.setOnClickListener {
            startActivity<SignUpActivity>()
        }
    }
}
