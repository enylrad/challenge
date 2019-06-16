package com.company.app.ui.base

import androidx.fragment.app.Fragment
import com.company.app.ui.MainActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    private val disposable = CompositeDisposable()

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    fun mainActivity(): MainActivity? {
        return activity as? MainActivity
    }

}