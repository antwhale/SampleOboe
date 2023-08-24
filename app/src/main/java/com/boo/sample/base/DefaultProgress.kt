package com.boo.sample.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.boo.sample.main.R

class DefaultProgress(
    context: Context
) : Dialog(context, R.style.BaseProgressTheme){
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.default_progress)
        setCancelable(false)
    }

    fun show(isShow: Boolean) {
        if(isShow){
            show()
        } else {
            dismiss()
        }
    }
}