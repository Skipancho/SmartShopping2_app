package com.jjsh.smartshopping.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jjsh.smartshopping.domain.usecase.SigninUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor(
    private val signinUseCase: SigninUseCase
) : ViewModel() {
    val userId = MutableStateFlow("")
    val password = MutableStateFlow("")

    fun signin() {
        viewModelScope.launch {
            signinUseCase(userId.value, password.value)
                .onSuccess {
                    println("로그인 성공")
                }.onFailure {
                    println("로그인 실패")
                }
        }
    }
}