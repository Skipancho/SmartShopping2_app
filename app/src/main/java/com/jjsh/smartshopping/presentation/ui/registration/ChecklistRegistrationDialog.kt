package com.jjsh.smartshopping.presentation.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.jjsh.smartshopping.databinding.FragmentChecklistRegistrationDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChecklistRegistrationDialog : DialogFragment() {
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
    }

    companion object {
        fun newInstance(productId: Long): ChecklistRegistrationDialog {
            return ChecklistRegistrationDialog().apply {
                this.productId = productId
            }
        }
    }
}