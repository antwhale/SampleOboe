package com.boo.sample.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.boo.sample.base.utils.DialogExt.showAlert
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment<V : ViewDataBinding> : Fragment() {
    protected val TAG = this::class.java.simpleName
    protected lateinit var binding: V
    protected open val viewModel: BaseViewModel? = null

    private val compositeDisposable = CompositeDisposable()
    private val progress: DefaultProgress by lazy { DefaultProgress(requireContext()) }

    protected abstract val layoutResID: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = createBinding(inflater, container, layoutResID)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel?.isLoading?.observe(viewLifecycleOwner) { showProgress(it) }
        viewModel?.alert?.observe(viewLifecycleOwner) { showAlert(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    open fun createBinding(inflater: LayoutInflater, container: ViewGroup?, layoutResID: Int): V {
        return DataBindingUtil.inflate(inflater, layoutResID, container, false)
    }

    open fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    fun showProgress(isShow: Boolean) {
        progress.show(isShow)
    }
}