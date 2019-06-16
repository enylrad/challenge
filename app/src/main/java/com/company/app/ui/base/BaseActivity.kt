package com.company.app.ui.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.company.app.ui.dialog.ErrorDialog
import dagger.android.support.HasSupportFragmentInjector


abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {


    fun showErrorDialog(message: String, onConfirm: (() -> Unit)? = null) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val fragment = fm.findFragmentByTag(ErrorDialog.TAG)
        if (fragment == null) {
            ft.addToBackStack(null)
            val dialog = ErrorDialog.newInstance(message = message, onConfirm = onConfirm)
            dialog.show(ft, ErrorDialog.TAG)
        }
    }

    fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = currentFocus
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }
}