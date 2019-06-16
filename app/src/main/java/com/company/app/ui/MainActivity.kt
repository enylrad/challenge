package com.company.app.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.company.app.R
import com.company.app.databinding.ActivityMainBinding
import com.company.app.ui.base.BaseActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        initialiseView()
    }

    private fun initialiseView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}
