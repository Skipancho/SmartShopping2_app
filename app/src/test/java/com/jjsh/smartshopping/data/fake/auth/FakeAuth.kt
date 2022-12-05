package com.jjsh.smartshopping.data.fake.auth

import com.jjsh.smartshopping.common.Auth

class FakeAuth: Auth() {
    override var token: String? = "fakeToken"
    override var refreshToken: String? = "fakeRefreshToken"
    override var nickName: String? = "fakeNickName"
    override var userCode: Long = 1111L
    override var userId: String? = "fakeUserId"
    override var userName: String? = "fakeUserName"
}