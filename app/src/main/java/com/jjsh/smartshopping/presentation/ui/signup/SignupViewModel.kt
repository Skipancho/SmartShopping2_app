package com.jjsh.smartshopping.presentation.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.common.ErrorException
import com.jjsh.smartshopping.domain.usecase.SignupUseCase
import com.jjsh.smartshopping.domain.usecase.ValidateNickNameUseCase
import com.jjsh.smartshopping.domain.usecase.ValidateUserIdUseCase
import com.jjsh.smartshopping.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val validateUserIdUseCase: ValidateUserIdUseCase,
    private val validateNickNameUseCase: ValidateNickNameUseCase
) : ViewModel() {

    val userId = MutableStateFlow("")
    val password = MutableStateFlow("")
    val passwordCheck = MutableStateFlow("")
    val nickName = MutableStateFlow("")
    val userName = MutableStateFlow("")

    private val _idChecked = MutableStateFlow(true)
    val idChecked: StateFlow<Boolean> get() = _idChecked
    private val _passwordChecked = MutableStateFlow(false)
    val passwordChecked: StateFlow<Boolean> get() = _passwordChecked
    private val _nickNameChecked = MutableStateFlow(true)
    val nickNameChecked: StateFlow<Boolean> get() = _nickNameChecked

    private val _idCheckEvent = MutableSharedFlow<UiState<Unit>>()
    val idCheckEvent: SharedFlow<UiState<Unit>> get() = _idCheckEvent
    private val _nickNameCheckEvent = MutableSharedFlow<UiState<Unit>>()
    val nickNameCheckEvent: SharedFlow<UiState<Unit>> get() = _nickNameCheckEvent

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Init)
    val uiState: StateFlow<UiState<Unit>> get() = _uiState

    private val _isProgressOn = MutableStateFlow(false)
    val isProgressOn: StateFlow<Boolean> get() = _isProgressOn

    fun signup() {
        if (!idChecked.value || !nickNameChecked.value) {
            _uiState.value = UiState.Error(ErrorException.SignupCheckedException)
            return
        }
        if (!passwordChecked.value) {
            _uiState.value = UiState.Error(ErrorException.PasswordDiffException)
            return
        }
        if (userId.value.isEmpty() || password.value.isEmpty() || passwordCheck.value.isEmpty()
            || nickName.value.isEmpty() || userName.value.isEmpty()
        ) {
            _uiState.value = UiState.Error(ErrorException.EmptyMemberException)
            return
        }
        viewModelScope.launch {
            signupUseCase(userId.value, password.value, nickName.value, userName.value)
                .onSuccess {
                    _uiState.value = UiState.Success(it)
                }.onFailure {
                    _uiState.value = UiState.Error(it)
                }
        }
    }

    fun isPasswordValidated(): Flow<Boolean> =
        combine(password, passwordCheck) { p, pc ->
            _passwordChecked.value = p == pc
            p == pc
        }


    fun isUserIdValidated() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            validateUserIdUseCase(userId.value)
                .onSuccess {
                    _idCheckEvent.emit(UiState.Success(it))
                    _idChecked.value = true
                }.onFailure {
                    _idCheckEvent.emit(UiState.Error(it))
                    _idChecked.value = false
                }
        }
    }

    fun isNickNameValidated() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            validateNickNameUseCase(nickName.value)
                .onSuccess {
                    _nickNameCheckEvent.emit(UiState.Success(it))
                    _nickNameChecked.value = true
                }.onFailure {
                    _nickNameCheckEvent.emit(UiState.Error(it))
                    _nickNameChecked.value = false
                }
        }
    }

    fun setIdCheck(check: Boolean) {
        _idChecked.value = check
    }

    fun setNickNameCheck(check: Boolean) {
        _nickNameChecked.value = check
    }

    fun initUiState() {
        _uiState.value = UiState.Init
    }

    fun setProgress(isProgressBarOn: Boolean) {
        _isProgressOn.value = isProgressBarOn
    }
}