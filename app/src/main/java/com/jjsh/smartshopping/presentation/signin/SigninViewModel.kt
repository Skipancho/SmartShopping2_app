package com.jjsh.smartshopping.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.usecase.SigninUseCase
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val signinUseCase: SigninUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val uiState: StateFlow<UiState<Unit>> get() = _uiState

    private val _isProgressOn = MutableStateFlow(false)
    val isProgressOn: StateFlow<Boolean> get() = _isProgressOn

    val userId = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun signin() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            signinUseCase(userId.value, password.value)
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }.onFailure {
                    _uiState.value = UiState.Error(it)
                }
        }
    }

    fun setProgress(isProgressBarOn: Boolean) {
        _isProgressOn.value = isProgressBarOn
    }
}