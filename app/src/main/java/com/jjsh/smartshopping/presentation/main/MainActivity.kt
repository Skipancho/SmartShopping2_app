package com.jjsh.smartshopping.presentation.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityMainBinding
import com.jjsh.smartshopping.presentation.adapter.ViewPagerAdapter
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.extension.start
import com.jjsh.smartshopping.presentation.main.cart.CartFragment
import com.jjsh.smartshopping.presentation.main.chart.ChartFragment
import com.jjsh.smartshopping.presentation.main.checklist.CheckListFragment
import com.jjsh.smartshopping.presentation.main.home.HomeFragment
import com.jjsh.smartshopping.presentation.mypage.MypageActivity
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
        initActionBar()
        initView()
    }

    private fun initActionBar() {
        setSupportActionBar(binding.mtbToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_home_24)
    }

    private fun initView() {
        val titles = listOf(
            getString(R.string.text_home),
            getString(R.string.text_cart),
            getString(R.string.text_checklist),
            getString(R.string.text_chart)
        )
        with(binding) {
            vpFragments.adapter = ViewPagerAdapter(
                supportFragmentManager,
                lifecycle,
                fragments
            )
            vpFragments.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        mtbToolbar.title = titles[position]
                        supportActionBar?.title = titles[position]
                        bnvNavigation.menu.getItem(position).isChecked = true
                    }
                }
            )
            bnvNavigation.setOnItemSelectedListener {
                when (it.itemId) {
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_appbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                binding.vpFragments.currentItem = 0
            }
            R.id.item_mypage -> {
                start<MypageActivity>()
                overridePendingTransition(R.anim.anim_right_in, R.anim.anim_default)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.vpFragments.currentItem > 0) {
            binding.vpFragments.currentItem = 0
            return
        }
        super.onBackPressed()
    }
}
