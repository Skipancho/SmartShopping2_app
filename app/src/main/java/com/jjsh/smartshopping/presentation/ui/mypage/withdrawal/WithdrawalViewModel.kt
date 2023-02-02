package com.jjsh.smartshopping.presentation.ui.mypage.withdrawal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.domain.repository.AuthRepository
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawalViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _withdrawalEvent = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val withdrawalEvent: StateFlow<UiState<Unit>> get() = _withdrawalEvent

    val agreeToWithdrawal = MutableStateFlow(false)
    val userIdText = MutableStateFlow("")

    fun withdrawal() {
        if (isDisagree()) {
            _withdrawalEvent.value = UiState.Error(ErrorException.DisagreeException)
            return
        }
        viewModelScope.launch {
            authRepository.withdrawal(userIdText.value)
                .onSuccess {
                    _withdrawalEvent.value = UiState.Success(it)
                }.onFailure {
                    _withdrawalEvent.value = UiState.Error(it)
                }
        }
    }

    private fun isDisagree() = !agreeToWithdrawal.value
}