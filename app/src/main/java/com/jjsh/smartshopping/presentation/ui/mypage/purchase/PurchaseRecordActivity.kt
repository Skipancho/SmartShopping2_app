package com.jjsh.smartshopping.presentation.ui.mypage.purchase

import android.os.Bundle
import androidx.activity.viewModels
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityPurchaseRecordBinding
import com.jjsh.smartshopping.presentation.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PurchaseRecordActivity : BaseActivity<ActivityPurchaseRecordBinding>(R.layout.activity_purchase_record) {

    private val viewModel by viewModels<PurchaseRecordViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}