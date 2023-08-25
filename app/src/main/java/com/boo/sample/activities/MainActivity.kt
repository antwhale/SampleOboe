package com.boo.sample.activities

import android.content.Context
import android.content.res.AssetManager
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.boo.sample.base.BaseActivity
import com.boo.sample.main.R
import com.boo.sample.main.databinding.ActivityMainBinding
import com.boo.sample.oboe.OboeUtil
import com.boo.sample.oboe.oboeSetDefaultStreamValue
import com.boo.sample.oboe.oboeStart
import com.boo.sample.oboe.oboeStop
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

        val myAudioMgr = baseContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val sampleRateStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_SAMPLE_RATE)
        val defaultSampleRate = Integer.parseInt(sampleRateStr)
        val framesPerBurstStr = myAudioMgr.getProperty(AudioManager.PROPERTY_OUTPUT_FRAMES_PER_BUFFER)
        val defaultFramesPerBurst = Integer.parseInt(framesPerBurstStr)

        oboeSetDefaultStreamValue(defaultSampleRate, defaultFramesPerBurst)

        findViewById<Button>(R.id.startBtn).setOnClickListener {
            Log.d(TAG, "startBtn click")

            oboeStart(assets)
        }

        findViewById<Button>(R.id.stopBtn).setOnClickListener {
            Log.d(TAG, "stopBtn click")

            oboeStop()
        }
//        binding.testView.text = stringFromJNI()
//        Log.d(TAG, "I am main")
//        Log.d(TAG, "JNI call : ${stringFromJNI()}")
    }

}