package com.example.primasaapp_mvil

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.primasaapp_mvil.view.login.LoginActivity
import com.example.primasaapp_mvil.view.splash.SplashActivity

class MainActivity : AppCompatActivity() {

    private val prefs by lazy {
        getSharedPreferences("onboarding", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isFirstTime = prefs.getBoolean("first_time", true)

        if (isFirstTime) {
            // Marcar como ya mostrado
            prefs.edit().putBoolean("first_time", false).apply()

            // Ir al onboarding
            startActivity(Intent(this, SplashActivity::class.java))
        } else {
            // Ir al login
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Finaliza MainActivity para que no quede en el backstack
        finish()
    }
}
