package com.dsoft.presentation.common

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    abstract fun showProgressBar()
    abstract fun hideProgressBar()
}