package com.umc.ttoklip.data.api

import com.umc.ttoklip.data.model.honeytip.CreateHoneyTipResponse
import com.umc.ttoklip.data.model.ResponseBody
import com.umc.ttoklip.data.model.honeytip.HoneyTipMainResponse
import com.umc.ttoklip.data.model.honeytip.InquireHoneyTipResponse
import com.umc.ttoklip.data.model.honeytip.InquireQuestionResponse
import com.umc.ttoklip.data.model.honeytip.request.ReportRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface HoneyTipApi {
    @GET("/api/v1/common/main")
    suspend fun getHoneyTipMain(): Response<ResponseBody<HoneyTipMainResponse>>

    //꿀팁
    @Multipart
    @POST("/api/v1/honeytips/posts")
    suspend fun postNewHoneyTip(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("category") category: RequestBody,
        @Part images: Array<MultipartBody.Part>,
        @Part("url") url: RequestBody
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    @GET("/api/v1/honeytips/posts/{postId}")
    suspend fun getHoneyTip(@Path("postId") honeyTipId: Int): Response<ResponseBody<InquireHoneyTipResponse>>

    @POST("/api/v1/honeytips/posts/scrap/{postId}")
    suspend fun postHoneyTipScrap(@Path("postId") honeyTipId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @DELETE("/api/v1/honeytips/posts/scrap/{postId}")
    suspend fun deleteHoneyTipScrap(@Path("postId") honeyTipId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @POST("/api/v1/honeytips/posts/report/{postId}")
    suspend fun postReportHoneyTip(
        @Path("postId") honeyTipId: Int,
        @Body request: ReportRequest
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    @POST("/api/v1/honeytips/posts/like/{postId}")
    suspend fun postLikeHoneyTip(@Path("postId") honeyTipId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @DELETE("/api/v1/honeytips/posts/like/{postId}")
    suspend fun deleteLikeHoneyTip(@Path("postId") honeyTipId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @DELETE("/api/v1/honeytips/posts/{postId}")
    suspend fun deleteHoneyTip(@Path("postId") honeyTipId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @Multipart
    @PATCH("/api/v1/honeytips/posts/{postId}")
    suspend fun patchHoneyTip(
        @Path("postId") honeyTipId: Int,
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("category") category: RequestBody,
        @Part images: Array<MultipartBody.Part>,
        @Part("url") url: RequestBody
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    //꿀팁 댓글
    @POST("/api/v1/honeytip/comment/{postId}")
    suspend fun postCommentHoneyTip(@Path("postId") honeyTipId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @POST("/api/v1/honeytip/comment/report/{commentId}")
    suspend fun postReportCommentHoneyTip(@Path("commentId") honeyTipId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @DELETE("/api/v1/honeytip/comment/{commentId}")
    suspend fun deleteCommentHoneyTip(@Path("commentId") honeyTipId: Int): Response<ResponseBody<CreateHoneyTipResponse>>


    //질문
    @Multipart
    @POST("/api/v1/question/post")
    suspend fun postNewQuestion(
        @Part("title") title: RequestBody,
        @Part("content") content: RequestBody,
        @Part("category") category: RequestBody,
        @Part images: Array<MultipartBody.Part>
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    @GET("/api/v1/question/post/{postId}")
    suspend fun getQuestion(@Path("postId") questionId: Int): Response<ResponseBody<InquireQuestionResponse>>

    @POST("/api/v1/question/post/report/{postId}")
    suspend fun postReportQuestion(
        @Path("postId") questionId: Int,
        @Body request: ReportRequest
    ): Response<ResponseBody<CreateHoneyTipResponse>>

    //질문 댓글
    @POST("/api/v1/question/comment/{postId}")
    suspend fun postCommentQuestion(@Path("postId") questionId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @POST("/api/v1/question/comment/report/{commentId}")
    suspend fun postReportCommentQuestion(@Path("commentId") questionId: Int): Response<ResponseBody<CreateHoneyTipResponse>>

    @DELETE("/api/v1/question/comment/{commentId}")
    suspend fun deleteCommentQuestion(@Path("commentId") questionId: Int): Response<ResponseBody<CreateHoneyTipResponse>>
}