package com.jjsh.smartshopping.presentation.ui.registration.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentReviewTextBinding
import com.jjsh.smartshopping.presentation.base.BaseFragment

class ReviewTextFragment : BaseFragment<FragmentReviewTextBinding>(R.layout.fragment_review_text) {

    private val activityViewModel by activityViewModels<ReviewRegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = activityViewModel
    }
}