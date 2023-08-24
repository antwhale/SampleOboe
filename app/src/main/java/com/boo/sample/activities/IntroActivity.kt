package com.boo.sample.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.boo.sample.base.BaseActivity
import com.boo.sample.main.R
import com.boo.sample.main.databinding.ActivityIntroBinding
import com.boo.sample.viewmodels.IntroViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : BaseActivity<ActivityIntroBinding>() {
    val TAG = IntroActivity::class.java.simpleName

    override val layoutResId: Int
        get() = R.layout.activity_intro

    private val introViewModel : IntroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        goToMainActivity()
    }

    private fun goToMainActivity() {
        Handler(mainLooper).postDelayed({
            val mainIntent = Intent(applicationContext, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, 1000)
    }
}