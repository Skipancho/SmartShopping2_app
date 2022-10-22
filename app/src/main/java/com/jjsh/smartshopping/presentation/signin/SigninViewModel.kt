package com.jjsh.smartshopping.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.common.Auth
import com.jjsh.smartshopping.domain.usecase.SigninUseCase
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val signinUseCase: SigninUseCase,
    auth: Auth
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val uiState: StateFlow<UiState<Unit>> get() = _uiState

    private val _isProgressOn = MutableStateFlow(false)
    val isProgressOn: StateFlow<Boolean> get() = _isProgressOn

    private val _moveToSignupEvent = MutableStateFlow(false)
    val moveToSignupEvent : StateFlow<Boolean> get() = _moveToSignupEvent

    val userId = MutableStateFlow("")
    val password = MutableStateFlow("")

    init {
       if (auth.token != null)
           _uiState.value = UiState.Success(Unit)
    }

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

    fun moveToSignup(){
        _moveToSignupEvent.value = true
    }

    fun initEvent(){
        _moveToSignupEvent.value = false
    }
}