package com.jjsh.smartshopping.presentation.ui.main.chart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.common.DateUtil
import com.jjsh.smartshopping.domain.model.PieChartData
import com.jjsh.smartshopping.domain.usecase.GetPieChartDataUseCase
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(
    private val getPieChartDataUseCase: GetPieChartDataUseCase
) : ViewModel() {

    private val _chartData = MutableStateFlow<UiState<List<PieChartData>>>(UiState.Init)
    val chartData: StateFlow<UiState<List<PieChartData>>> get() = _chartData

    private val _currentYear = MutableStateFlow(DateUtil.year())
    val currentYear: StateFlow<Int> get() = _currentYear

    private val _currentMonth = MutableStateFlow(DateUtil.month())
    val currentMonth: StateFlow<Int> get() = _currentMonth

    fun getChartData() {
        viewModelScope.launch {
            getPieChartDataUseCase(currentYear.value, currentMonth.value)
                .onSuccess {
                    _chartData.value = UiState.Success(it)
                }.onFailure {
                    _chartData.value = UiState.Error(it)
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
        getChartData()
    }

    fun prevMonth() {
        val cur = currentMonth.value
        if (cur == 1) {
            _currentYear.value -= 1
            _currentMonth.value = 12
        } else {
            _currentMonth.value -= 1
        }
        getChartData()
    }
}