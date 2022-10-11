package com.jjsh.smartshopping.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityMainBinding
import com.jjsh.smartshopping.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_main

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
    }
}
