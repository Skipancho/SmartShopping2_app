package com.jjsh.smartshopping.presentation.ui.registration.product

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.common.FileUtil
import com.jjsh.smartshopping.databinding.ActivityProductRegistrationBinding
import com.jjsh.smartshopping.databinding.ItemDetailImageBinding
import com.jjsh.smartshopping.presentation.UiState
import com.jjsh.smartshopping.presentation.base.BaseActivity
import com.jjsh.smartshopping.presentation.base.BaseAdapter
import com.jjsh.smartshopping.presentation.decoration.VerticalItemDecoration
import com.jjsh.smartshopping.presentation.extension.dpToPx
import com.jjsh.smartshopping.presentation.extension.errorHandling
import com.jjsh.smartshopping.presentation.extension.shortToast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProductRegistrationActivity :
    BaseActivity<ActivityProductRegistrationBinding>(R.layout.activity_product_registration) {


    private val viewModel by viewModels<ProductRegistrationViewModel>()

    private val imageAdapter by lazy { ImageAdapter() }

    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK){
            it.data?.data?.let { uri ->
                viewModel.addImageFromUri(uri){ list ->
                    imageAdapter.submitList(list.toMutableList())
                }
                viewModel.addImageFile(FileUtil.uriToFile(this,uri))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel

        initView()
        setEvent()
        observeData()
    }

    private fun initView() {
        binding.rvDetailImages.run {
            adapter = imageAdapter
            layoutManager = LinearLayoutManager(
                this@ProductRegistrationActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            addItemDecoration(
                VerticalItemDecoration(width = 8.dpToPx())
            )
        }

        binding.spinnerProductCategory.run {
            adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.category)
            )
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    p3: Long
                ) {
                    Timber.e("$position")
                    viewModel.setCategoryNumber(position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // do nothing
                }
            }
        }
    }

    private fun setEvent() {
        binding.ivAddImage.setOnClickListener {
            pickImage()
        }
    }

    private fun pickImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
        }

        pickImageLauncher.launch(intent)
    }

    private fun observeData() {
        observeFlowWithLifecycle(viewModel.uiState) {
            when (it) {
                is UiState.Success -> {
                    shortToast("성공")
                    finish()
                }
                is UiState.Error -> {
                    errorHandling(it.err)
                }
                else -> {}
            }
        }
    }

    class ImageAdapter : BaseAdapter<ItemDetailImageBinding, Uri>(
        checkItemsTheSame = { old, new -> old == new },
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