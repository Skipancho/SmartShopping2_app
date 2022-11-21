package com.jjsh.smartshopping.presentation.ui.registration.checklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.common.ImageLoader
import com.jjsh.smartshopping.databinding.FragmentChecklistRegistrationDialogBinding
import com.jjsh.smartshopping.presentation.UiEvent
import com.jjsh.smartshopping.presentation.extension.errorHandling
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ChecklistRegistrationDialog : BottomSheetDialogFragment() {
    private var productId: Long = -1L

    private var _binding: FragmentChecklistRegistrationDialogBinding? = null
    private val binding get() = _binding ?: error("binding not initialized")

    private val viewModel by viewModels<ChecklistRegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChecklistRegistrationDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        observeData()
        setEvent()

        viewModel.getProductDetail(productId)
    }

    private fun observeData() {
        viewModel.getProductEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is UiEvent.Success -> {
                        setProductImage(it.data.thumbnailPath)
                    }
                    is UiEvent.Error -> {
                        requireContext().errorHandling(it.err)
                    }
                }
            }.launchIn(lifecycleScope)

        viewModel.addCheckItemEvent.flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                when(it){
                    is UiEvent.Success -> {
                        dialog?.dismiss()
                    }
                    is UiEvent.Error -> {
                        requireContext().errorHandling(it.err)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun setProductImage(thumbnailUrl : String){
        ImageLoader(binding.ivImage,requireContext())
            .setPlaceHolder(R.drawable.icon)
            .setErrorImage(R.drawable.icon)
            .loadImage(thumbnailUrl)
    }

    private fun setEvent() {
        binding.tvCancel.setOnClickListener {
            dialog?.dismiss()
        }
    }

    companion object {
        fun newInstance(productId: Long): ChecklistRegistrationDialog {
            return ChecklistRegistrationDialog().apply {
                this.productId = productId
            }
        }
    }
}