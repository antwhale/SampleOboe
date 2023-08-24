package com.boo.sample.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.boo.sample.base.BaseActivity
import com.boo.sample.main.R
import com.boo.sample.main.databinding.ActivityMainBinding
import com.boo.sample.oboe.OboeUtil
import com.boo.sample.oboe.stringFromJNI
import com.boo.sample.repository.preference.PreferenceRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    val TAG = this::class.java.simpleName

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        System.loadLibrary("native-lib")

        binding.testView.text = stringFromJNI()
        Log.d(TAG, "I am main")
        Log.d(TAG, "JNI call : ${stringFromJNI()}")
    }
}