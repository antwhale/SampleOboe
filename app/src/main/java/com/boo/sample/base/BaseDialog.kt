package com.boo.sample.base

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.boo.sample.main.R

abstract class BaseDialog<V : ViewDataBinding>(
    context: Context,
    themeRes : Int = R.style.BaseDialogTheme
) : Dialog(context, themeRes) {
    protected val TAG = this::class.java.simpleName
    protected lateinit var binding: V

    override fun setContentView(layoutResID: Int) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutResID, null, false)
        super.setContentView(layoutResID)
    }
}