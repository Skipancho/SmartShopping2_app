package com.jjsh.smartshopping.presentation.ui.registration.review

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.FragmentReviewScoreBinding
import com.jjsh.smartshopping.presentation.base.BaseFragment

class ReviewScoreFragment : BaseFragment<FragmentReviewScoreBinding>(R.layout.fragment_review_score) {

    private val activityViewModel by activityViewModels<ReviewRegistrationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = activityViewModel
    }
}