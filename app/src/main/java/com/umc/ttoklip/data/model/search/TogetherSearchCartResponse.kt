package com.umc.ttoklip.data.model.search

data class TogetherSearchCartResponse (
    val id:Int,
    val title:String,
    val location:String,
    val totalPrice:Int,
    val partyMax:Int,
    val writer:String,
    val partyCnt:Int,
    val commentCount:Int,
    val currentPrice:Int,
    val tradeStatus:String
){
    fun toModel(category: String, bigCategory: Int) = SearchModel(
        category, commentCount, location, id, title, bigCategory , 0, 0 , writer ?: ""
    )
}