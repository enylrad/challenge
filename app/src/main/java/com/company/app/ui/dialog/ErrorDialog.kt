package com.company.app.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import com.company.app.R
import com.company.app.databinding.DialogErrorBinding
import com.company.app.ui.base.BaseDialog

class ErrorDialog : BaseDialog() {

    private var mMessage: String? = null
    private var mOnClickConfirm: (() -> Unit)? = null

    private lateinit var binding: DialogErrorBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val window = dialog.window
        window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_error, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configMessage()
        configBtnConfirm()
    }

    override fun onStart() {
        super.onStart()
        setDialogWindowWidth(0.85)
    }

    private fun configBtnConfirm() {
        binding.btnConfirm.setOnClickListener {
            mOnClickConfirm?.invoke()
            dismiss()
        }
    }

    private fun configMessage() {
        binding.message.text = mMessage
    }

    companion object {
        val TAG = ErrorDialog::class.java.name ?: ""

        fun newInstance(message: String?, onConfirm: (() -> Unit)? = null): ErrorDialog {
            val fragment = ErrorDialog()
            fragment.mMessage = message
            fragment.mOnClickConfirm = onConfirm
            return fragment
        }
    }
}
