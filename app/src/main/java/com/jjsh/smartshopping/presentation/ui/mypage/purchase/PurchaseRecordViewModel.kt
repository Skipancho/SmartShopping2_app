package com.jjsh.smartshopping.presentation.ui.mypage.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.common.DateUtil
import com.jjsh.smartshopping.domain.model.Purchase
import com.jjsh.smartshopping.domain.repository.PurchaseRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PurchaseRecordViewModel @Inject constructor(
    private val purchaseRepository: PurchaseRepository
) : ViewModel() {

    private val _purchaseRecord = MutableStateFlow<UiState<List<Purchase>>>(UiState.Init)
    val purchaseRecord: StateFlow<UiState<List<Purchase>>> get() = _purchaseRecord

    private val _currentYear = MutableStateFlow(DateUtil.year())
    val currentYear: StateFlow<Int> get() = _currentYear

    private val _currentMonth = MutableStateFlow(DateUtil.month())
    val currentMonth: StateFlow<Int> get() = _currentMonth

    fun getPurchaseRecord() {
        viewModelScope.launch {
            purchaseRepository.getPurchaseRecord(currentYear.value, currentMonth.value)
                .onSuccess {
                    _purchaseRecord.value = UiState.Success(it)
                }.onFailure {
                    _purchaseRecord.value = UiState.Error(it)
                }
        }
    }

    fun nextMonth() {
        val cur = currentMonth.value
        if (cur == 12) {
            _currentYear.value += 1
            _currentMonth.value = 1
        } else {
            _currentMonth.value += 1
        }
        getPurchaseRecord()
    }

    fun prevMonth() {
        val cur = currentMonth.value
        if (cur == 1) {
            _currentYear.value -= 1
            _currentMonth.value = 12
        } else {
            _currentMonth.value -= 1
        }
        getPurchaseRecord()
    }
}