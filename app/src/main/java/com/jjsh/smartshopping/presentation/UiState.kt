package com.jjsh.smartshopping.presentation

sealed class UiState<out T> {
    object Init : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val err: Throwable) : UiState<Nothing>()
}