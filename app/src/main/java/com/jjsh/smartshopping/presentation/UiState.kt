package com.jjsh.smartshopping.presentation

/*
Ui 상태 변화 필요시 사용
 */
sealed class UiState<out T> {
    object Init : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val err: Throwable) : UiState<Nothing>()
}

/*
Ui에 큰 영향을 주지 않는 이벤트에 사용
 */
sealed class UiEvent<out T> {
    data class Success<T>(val data: T) : UiEvent<T>()
    data class Error(val err: Throwable) : UiEvent<Nothing>()
}