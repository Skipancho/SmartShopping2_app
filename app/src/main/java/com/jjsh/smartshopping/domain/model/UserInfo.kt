package com.jjsh.smartshopping.domain.model

data class UserInfo(
    private val _userId: String?,
    private val _name: String?,
    private val _nickName: String?
) {
    val userId get() = _userId ?: ""
    val name get() = _name ?: ""
    val nickname get() = _nickName ?: ""
}
