package com.umc.ttoklip.di

import com.umc.ttoklip.data.api.HomeApi
import com.umc.ttoklip.data.api.MainCommsApi
import com.umc.ttoklip.data.api.MainTogethersApi
import com.umc.ttoklip.data.api.MyAccountRestrictApi
import com.umc.ttoklip.data.api.MyBlockUserApi
import com.umc.ttoklip.data.api.MyPage2Api
import com.umc.ttoklip.data.api.MyPageApi
import com.umc.ttoklip.data.api.MyPostApi
import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.api.ReadCommsApi
import com.umc.ttoklip.data.api.ReadTogetherApi
import com.umc.ttoklip.data.api.Search2Api
import com.umc.ttoklip.data.api.SearchApi
import com.umc.ttoklip.data.api.TownMainApi
import com.umc.ttoklip.data.api.WriteCommsApi
import com.umc.ttoklip.data.api.WriteTogetherApi
import com.umc.ttoklip.data.repository.home.HomeRepository
import com.umc.ttoklip.data.repository.home.HomeRepositoryImpl
import com.umc.ttoklip.data.repository.mypage.MyAccountRestrictRepository
import com.umc.ttoklip.data.repository.mypage.MyAccountRestrictRepositoryImpl
import com.umc.ttoklip.data.repository.mypage.MyBlockUserRepository
import com.umc.ttoklip.data.repository.mypage.MyBlockUserRepositoryImpl
import com.umc.ttoklip.data.repository.mypage.MyPageRepository2
import com.umc.ttoklip.data.repository.mypage.MyPageRepository2Impl
import com.umc.ttoklip.data.repository.mypage.MyPostRepository
import com.umc.ttoklip.data.repository.mypage.MyPostRepositoryImpl
import com.umc.ttoklip.data.repository.news.NewsRepository
import com.umc.ttoklip.data.repository.news.NewsRepositoryImpl
import com.umc.ttoklip.data.repository.scrap.ScrapRepository
import com.umc.ttoklip.data.repository.scrap.ScrapRepositoryImpl
import com.umc.ttoklip.data.repository.search.Search2Repository
import com.umc.ttoklip.data.repository.search.Search2RepositoryImpl
import com.umc.ttoklip.data.repository.search.SearchRepository
import com.umc.ttoklip.data.repository.search.SearchRepositoryImpl
import com.umc.ttoklip.data.repository.town.MainCommsRepository
import com.umc.ttoklip.data.repository.town.MainCommsRepositoryImpl
import com.umc.ttoklip.data.repository.town.MainTogethersRepository
import com.umc.ttoklip.data.repository.town.MainTogethersRepositoryImpl
import com.umc.ttoklip.data.repository.town.ReadCommsRepository
import com.umc.ttoklip.data.repository.town.ReadCommsRepositoryImpl
import com.umc.ttoklip.data.repository.town.ReadTogetherRepository
import com.umc.ttoklip.data.repository.town.ReadTogetherRepositoryImpl
import com.umc.ttoklip.data.repository.town.TownMainRepository
import com.umc.ttoklip.data.repository.town.TownMainRepositoryImpl
import com.umc.ttoklip.data.repository.town.WriteCommsRepository
import com.umc.ttoklip.data.repository.town.WriteCommsRepositoryImpl
import com.umc.ttoklip.data.repository.town.WriteTogetherRepository
import com.umc.ttoklip.data.repository.town.WriteTogetherRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Provides
    @Singleton
    fun providesNewsRepository(api: NewsApi): NewsRepository =
        NewsRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesReadCommsRepository(api: ReadCommsApi): ReadCommsRepository =
        ReadCommsRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesReadTogetherRepository(api: ReadTogetherApi): ReadTogetherRepository =
        ReadTogetherRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesWriteCommsRepository(api: WriteCommsApi): WriteCommsRepository =
        WriteCommsRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesWriteTogetherRepository(api: WriteTogetherApi): WriteTogetherRepository =
        WriteTogetherRepositoryImpl(api)


    @Provides
    @Singleton
    fun providesSearchRepository(api: SearchApi): SearchRepository =
        SearchRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesSearch2Repository(api: Search2Api): Search2Repository =
        Search2RepositoryImpl(api)


    @Provides
    @Singleton
    fun providesMainCommsRepository(api: MainCommsApi): MainCommsRepository =
        MainCommsRepositoryImpl(api)


    @Provides
    @Singleton
    fun providesMainTogethersRepository(api: MainTogethersApi): MainTogethersRepository =
        MainTogethersRepositoryImpl(api)


    @Provides
    @Singleton
    fun providesScrapRepository(api: MyPageApi): ScrapRepository =
        ScrapRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesHomeRepository(api: HomeApi): HomeRepository =
        HomeRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesMyPage2Repository(api: MyPage2Api): MyPageRepository2 =
        MyPageRepository2Impl(api)

    @Provides
    @Singleton
    fun providesMyAccountRestrictRepository(api: MyAccountRestrictApi): MyAccountRestrictRepository =
        MyAccountRestrictRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesMyPostRepository(api: MyPostApi): MyPostRepository =
        MyPostRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesMyBlockUserRepository(api: MyBlockUserApi): MyBlockUserRepository =
        MyBlockUserRepositoryImpl(api)

    @Provides
    @Singleton
    fun providesTownMainRepository(api: TownMainApi): TownMainRepository =
        TownMainRepositoryImpl(api)

}