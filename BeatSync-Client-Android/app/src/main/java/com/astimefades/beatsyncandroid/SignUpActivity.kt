package com.astimefades.beatsyncandroid

import kotlinx.android.synthetic.main.content_sign_up.*

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.RetrofitInstance
import com.astimefades.beatsyncandroid.model.request.CreateAccountRequest
import com.astimefades.beatsyncandroid.model.request.Request
import com.astimefades.beatsyncandroid.web.AccountService
import org.jetbrains.anko.doAsync

class SignUpActivity : AppCompatActivity() {

    private val accountService = RetrofitInstance.retrofit.create(AccountService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpSubmit.setOnClickListener {

            val request = Request(CreateAccountRequest().apply {
                email = signUpEmail.text.toString(); password = signUpPassword.text.toString()
            })
            val createAccountRequest = accountService.createAccount(request)
            doAsync() {
                val account = createAccountRequest.execute()
                account.toString()
            }
            Toast.makeText(this@SignUpActivity, "Account created", Toast.LENGTH_SHORT).show()
        }
    }
}
