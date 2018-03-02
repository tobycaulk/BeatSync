package com.astimefades.beatsyncandroid

import kotlinx.android.synthetic.main.content_sign_up.*

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        signUpSubmit.setOnClickListener {
            doAsync {
                val response = persistenceApi.createAccount(Request(CreateAccountRequest().apply {
                    email = signUpEmail.text.toString()
                    password = signUpPassword.text.toString()
                })).execute().body()
                if(response?.errorDescription != null) {
                    uiThread {
                        Toast.makeText(this@SignUpActivity, response.errorDescription, Toast.LENGTH_LONG).show()
                    }
                } else {
                    uiThread {
                        startActivity<MainActivity>()
                    }
                }
            }
        }
    }
}
