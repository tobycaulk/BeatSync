package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.content_login.*
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginSubmit.setOnClickListener {
            Toast.makeText(this@LoginActivity, "Login Clicked.", Toast.LENGTH_SHORT).show()
        }

        loginSignUp.setOnClickListener {
            startActivity<SignUpActivity>()
            Toast.makeText(this@LoginActivity, "Sign Up Clicked.", Toast.LENGTH_SHORT).show()
        }
    }
}
