package com.umc.ttoklip.data.api

import com.umc.ttoklip.R
import com.umc.ttoklip.data.model.KakaoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KakaoApi {
    @GET("v2/local/search/address")
    fun getSearchKeyword(
        @Header("Authorization") key: String,
        @Query("query") query:String
    ): Call<KakaoResponse.ResultSearchKeyword>
}