package com.dsoft.presentation.common

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.dsoft.presentation.R

abstract class BaseFragment : Fragment() {
    abstract fun showProgressBar()
    abstract fun hideProgressBar()

    fun showErrorDialog(errorMessage: String) {
        val builder = AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.attention)
            setMessage(errorMessage)
            setPositiveButton(R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()
    }
}