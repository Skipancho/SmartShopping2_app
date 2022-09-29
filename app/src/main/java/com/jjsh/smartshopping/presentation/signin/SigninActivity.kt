package com.jjsh.smartshopping.presentation.signin

import android.os.Bundle
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivitySigninBinding
import com.jjsh.smartshopping.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninActivity : BaseActivity<ActivitySigninBinding>() {

    override val layoutRes: Int
        get() = R.layout.activity_signin

    private val viewModel by viewModels<SigninViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
    }
}