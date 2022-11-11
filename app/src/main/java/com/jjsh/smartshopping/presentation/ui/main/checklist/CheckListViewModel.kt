package com.jjsh.smartshopping.presentation.ui.main.checklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.CheckItem
import com.jjsh.smartshopping.domain.repository.CheckItemRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CheckListViewModel @Inject constructor(
    private val checkItemRepository: CheckItemRepository
) : ViewModel() {

    private val _checkList = MutableStateFlow<UiState<List<CheckItem>>>(UiState.Init)
    val checkList: StateFlow<UiState<List<CheckItem>>> get() = _checkList

    private val _deleteCheckedItems = MutableStateFlow(false)
    val deleteCheckedItems: StateFlow<Boolean> get() = _deleteCheckedItems

    fun getCheckList() {
        viewModelScope.launch {
            checkItemRepository.getCheckItems().collectLatest { result ->
                result.onSuccess {
                    _checkList.value = UiState.Success(it)
                }.onFailure {
                    _checkList.value = UiState.Error(it)
                }
            }
        }
    }

    fun updateCheckItem(vararg checkItem: CheckItem) {
        viewModelScope.launch {
            checkItemRepository.updateCheckItem(*checkItem)
                .onSuccess {
                    Timber.d("update success")
                }.onFailure {
                    Timber.e("update failure")
                }
        }
    }

    fun deleteCheckItem(vararg checkItem: CheckItem) {
        viewModelScope.launch {
            checkItemRepository.deleteCheckItem(*checkItem)
                .onSuccess {
                    Timber.d("delete success")
                }.onFailure {
                    Timber.e("delete failure")
                }
        }
    }

    fun checkAllItems() {
        val state = checkList.value
        if (state is UiState.Success) {
            val items = state.data.map { it.setChecked(true) }.toTypedArray()
            updateCheckItem(*items)
        }
    }

    fun startDeleteCheckedItems() {
        _deleteCheckedItems.value = true
    }

    fun deleteAllCheckedItems() {
        _deleteCheckedItems.value = false
        val state = checkList.value
        if (state is UiState.Success) {
            val items = state.data.filter { it.isChecked }.toTypedArray()
            deleteCheckItem(*items)
        }
    }
}