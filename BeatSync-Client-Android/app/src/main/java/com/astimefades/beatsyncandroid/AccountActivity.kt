package com.astimefades.beatsyncandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.astimefades.beatsyncandroid.model.config.AccountConfiguration
import com.astimefades.beatsyncandroid.service.web.PersistenceApi
import kotlinx.android.synthetic.main.activity_account.*
import kotlinx.android.synthetic.main.content_account.*
import org.jetbrains.anko.startActivity

class AccountActivity : BottomNavigationActivity() {

    private val accountConfiguration by lazy { AccountConfiguration(this@AccountActivity) }
    private val persistenceApi = PersistenceApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        bottomNavigation.setOnNavigationItemSelectedListener { item -> handleNavigationItemClicked(item) }

        accountLogout.setOnClickListener { handleLogout() }
    }

    private fun handleLogout() {
        persistenceApi.send(
                accountConfiguration.getProxyId(),
                persistenceApi::logoutAccount,
                { _ ->
                    accountConfiguration.removeAccountInformation()
                    Toast.makeText(this@AccountActivity, "Logged out!", Toast.LENGTH_LONG).show()
                    startActivity<LoginActivity>()
                },
                this@AccountActivity
        )
    }
}