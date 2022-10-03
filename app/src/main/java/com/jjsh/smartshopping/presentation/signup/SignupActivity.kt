package com.jjsh.smartshopping.presentation.signup

import android.os.Bundle
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivitySignupBinding
import com.jjsh.smartshopping.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : BaseActivity<ActivitySignupBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_signup

    private val viewModel = viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

}