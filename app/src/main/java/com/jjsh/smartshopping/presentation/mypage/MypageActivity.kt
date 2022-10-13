package com.jjsh.smartshopping.presentation.mypage

import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityMypageBinding
import com.jjsh.smartshopping.presentation.base.BaseActivity

class MypageActivity : BaseActivity<ActivityMypageBinding>(R.layout.activity_mypage) {

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.anim_default, R.anim.anim_right_out)
    }
}