package com.jjsh.smartshopping.presentation.customview

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.databinding.BindingAdapter
import com.jjsh.smartshopping.R
import com.jjsh.smartshopping.databinding.LayoutCustomSearchViewBinding

class SearchTextView(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    private var backButtonClickListener: (() -> Unit) = {}
    private var searchButtonClickListener: ((String) -> Unit) = {}
    private var deleteButtonClickListener: (() -> Unit) = { clearText() }
    private var textChangeListener: (() -> Unit) = {}
    private lateinit var binding: LayoutCustomSearchViewBinding

    val text: Editable
        get() = binding.etSearch.text

    var hint: CharSequence
        get() = binding.etSearch.hint
        set(value) {
            binding.etSearch.hint = value
        }

    init {
        layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        initView()
        matchEvent()
        observeText()
    }

    private fun initView() {
        val view =
            LayoutInflater.from(context).inflate(R.layout.layout_custom_search_view, this, false)
        binding = LayoutCustomSearchViewBinding.bind(view)
        addView(view)
    }

    private fun matchEvent() {
        with(binding) {
            ibBack.setOnClickListener { backButtonClickListener() }
            ibSearch.setOnClickListener { searchButtonClickListener(text.toString()) }
            ibDelete.setOnClickListener { deleteButtonClickListener() }
        }
    }

    private fun observeText() {
        binding.etSearch.addTextChangedListener {
            with(text.isNotEmpty()) {
                binding.ibDelete.isVisible = this
                textChangeListener
            }
        }
    }

    fun setDeleteButtonClickListener(action: () -> Unit) {
        deleteButtonClickListener = {
            action()
            clearText()
        }
    }

    fun setSearchButtonClickListener(action: (String) -> Unit) {
        searchButtonClickListener = action
    }

    fun setBackButtonClickListener(action: () -> Unit) {
        backButtonClickListener = action
    }

    fun setTextChangeListener(action: () -> Unit) {
        textChangeListener = action
    }

    fun setText(charSequence: CharSequence) {
        binding.etSearch.setText(charSequence)
    }

    private fun clearText() {
        binding.etSearch.text.clear()
    }
}

@BindingAdapter("hintText")
fun SearchTextView.setHint(text: String) {
    hint = text
}

@BindingAdapter("onSearchClick")
fun SearchTextView.setOnSearchClickListener(action: (String) -> Unit) {
    setSearchButtonClickListener(action)
}

@BindingAdapter("onDeleteClick")
fun SearchTextView.setOnDeleteClickListener(action: () -> Unit) {
    setDeleteButtonClickListener(action)
}

@BindingAdapter("onBackClick")
fun SearchTextView.setOnBackClickListener(action: () -> Unit) {
    setBackButtonClickListener(action)
}

@BindingAdapter("onTextChange")
fun SearchTextView.setOnTextChangeListener(action: () -> Unit) {
    setTextChangeListener(action)
}