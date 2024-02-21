package com.umc.ttoklip.data.model.mypage

data class MyPageInfoResponse(
    var street: String?,
    var nickname: String,
    var profileImage: String?,
    var independentYear: Int,
    var independentMonth: Int
){
    constructor(): this(null, "", null, 0, 0)
}
/*
street": null,
"nickname": "플램",
"profileImage": "https://ddoklipbk.s3.ap-northeast-2.amazonaws.com/photo/1000000033.jpg.jpg",
"independentYear": 0,
"independentMonth": 9*/
