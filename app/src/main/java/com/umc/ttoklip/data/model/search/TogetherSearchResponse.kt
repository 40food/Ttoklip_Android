package com.umc.ttoklip.data.model.search

data class TogetherSearchResponse(
    val isFirst: Boolean,
    val isLast: Boolean,
    val carts: List<TogetherSearchCartResponse>,
    val totalElements: Int,
    val totalPage: Int
)
