package com.jjsh.smartshopping.presentation.ui.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.model.UserInfo
import com.jjsh.smartshopping.domain.repository.AuthRepository
import com.jjsh.smartshopping.presentation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _userInfo = MutableStateFlow(UserInfo("","",""))
    val userInfo: StateFlow<UserInfo> get() = _userInfo

    private val _signOutEvent = MutableSharedFlow<UiEvent<Unit>>()
    val signOutEvent: SharedFlow<UiEvent<Unit>> get() = _signOutEvent

    init {
        _userInfo.value = authRepository.getUserInfo()
    }

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
            _signOutEvent.emit(UiEvent.Success(Unit))
        }
    }
}