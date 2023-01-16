package com.jjsh.smartshopping.presentation.ui.registration.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.ActivityProductRegistrationBinding
import com.jjsh.smartshopping.databinding.ItemDetailImageBinding
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.base.BaseAdapter
import com.jjsh.smartshopping.presentation.decoration.VerticalItemDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductRegistrationActivity :
    BaseActivity<ActivityProductRegistrationBinding>(R.layout.activity_product_registration) {


    private val viewModel by viewModels<ProductRegistrationViewModel>()

    private val imageAdapter by lazy { ImageAdapter() }

    private val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK){
            it.data?.data?.let { uri ->
                viewModel.addImageFromUri(uri){ list ->
                    imageAdapter.submitList(list.toMutableList())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initView()
        setEvent()
    }

    private fun initView() {
        binding.rvDetailImages.run {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(this@ProductRegistrationActivity,LinearLayoutManager.HORIZONTAL,false)
            addItemDecoration(
                VerticalItemDecoration(width = 8.dpToPx())
            )
        }
    }

    private fun setEvent(){
        binding.ivAddImage.setOnClickListener {
            pickImage()
        }
    }


    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }
        launcher.launch(intent)
    }

    class ImageAdapter : BaseAdapter<ItemDetailImageBinding, Uri>(
        checkItemsTheSame = {old , new -> old == new},
        R.layout.item_detail_image,
        bind = { binding, uri ->
            binding.root.layoutParams.run {
                width = 150.dpToPx()
                height = 150.dpToPx()
            }
            binding.ivItemImage.setImageURI(uri)
        }
    )
}