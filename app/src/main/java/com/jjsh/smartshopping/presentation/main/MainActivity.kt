package com.jjsh.smartshopping.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityMainBinding
import com.jjsh.smartshopping.presentation.adapter.ViewPagerAdapter
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.main.cart.CartFragment
import com.jjsh.smartshopping.presentation.main.chart.ChartFragment
import com.jjsh.smartshopping.presentation.main.checklist.CheckListFragment
import com.jjsh.smartshopping.presentation.main.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()
    private val fragments by lazy {
        listOf(
            HomeFragment(),
            CartFragment(),
            CheckListFragment(),
            ChartFragment()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initView()
    }

    private fun initView() {
        with(binding) {
            vpFragments.adapter = ViewPagerAdapter(
                supportFragmentManager,
                lifecycle,
                fragments
            )
            vpFragments.registerOnPageChangeCallback(
                object :ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        bnvNavigation.menu.getItem(position).isChecked = true
                    }
                }
            )
            bnvNavigation.setOnItemSelectedListener {
               when(it.itemId){
                   R.id.item_home -> {
                       vpFragments.currentItem = 0
                       true
                   }
                   R.id.item_cart -> {
                       vpFragments.currentItem = 1
                       true
                   }
                   R.id.item_checklist -> {
                       vpFragments.currentItem = 2
                       true
                   }
                   R.id.item_chart -> {
                       vpFragments.currentItem = 3
                       true
                   }
                   else -> false
               }
            }
        }
    }
}
