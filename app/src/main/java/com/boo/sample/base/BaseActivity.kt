package com.boo.sample.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<V : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: V

    val compositeDisposable = CompositeDisposable()
    private val progress: DefaultProgress by lazy { DefaultProgress(this) }

    protected open val requireLandscape: Boolean = false

    protected abstract val layoutResId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("BaseActivity", "I am a girl")
        requestedOrientation =
            if (requireLandscape) ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            else ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        binding = createBinding(layoutResId)
        binding.lifecycleOwner = this

        //viewModel?.isLoading?.observe(this){ showProgress(it) }
        //viewModel?.alert?.observe(this){ showAlert(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }



    open fun createBinding(layoutResId: Int): V = DataBindingUtil.setContentView(this, layoutResId)

    //fun showProgress(isShow: Boolean) = progress.show(isShow)
}