package com.umc.ttoklip.data.repository.mypage

import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.mypage.MyPageInfoResponse
import com.umc.ttoklip.data.model.signup.SignupResponse
import com.umc.ttoklip.module.NetworkResult
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Part

interface MyPageRepository2 {
    suspend fun getMyPageInfo(): NetworkResult<MyPageInfoResponse>
    suspend fun editMyPageInfo(
        photo: MultipartBody.Part?,
        info: MutableMap<String, RequestBody>, cate: List<MultipartBody.Part>?
    ): NetworkResult<CreateHoneyTipResponse>

    suspend fun checkNickname(nick:String): NetworkResult<SignupResponse>
}