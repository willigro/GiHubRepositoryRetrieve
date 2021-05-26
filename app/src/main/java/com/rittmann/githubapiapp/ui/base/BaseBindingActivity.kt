package com.rittmann.githubapiapp.ui.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rittmann.baselifecycle.base.BaseActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

abstract class BaseBindingActivity<T : ViewDataBinding>(private val resId: Int) : BaseActivity(),
    KodeinAware {

    override val kodein by kodein()

    protected lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, resId)
    }
}