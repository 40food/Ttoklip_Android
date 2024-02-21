package com.umc.ttoklip.data.model.town

data class Communities(
    val id: Long,
    val title: String,
    val content: String,
    val commentCount: Long,
    val likeCount: Long,
    val scrapCount: Long,
    val writer: String
)