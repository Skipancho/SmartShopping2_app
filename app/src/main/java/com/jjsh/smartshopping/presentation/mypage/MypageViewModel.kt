package com.jjsh.smartshopping.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.common.Auth
import com.jjsh.smartshopping.domain.model.UserInfo
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
    private val auth: Auth
) : ViewModel() {
    private val _userInfo = MutableStateFlow(UserInfo(auth.userId, auth.userName, auth.nickName))
    val userInfo: StateFlow<UserInfo> get() = _userInfo

    private val _signOutEvent = MutableSharedFlow<UiEvent<Unit>>()
    val signOutEvent: SharedFlow<UiEvent<Unit>> get() = _signOutEvent

    fun signOut() {
        viewModelScope.launch {
            auth.signOut()
            _signOutEvent.emit(UiEvent.Success(Unit))
        }
    }
}