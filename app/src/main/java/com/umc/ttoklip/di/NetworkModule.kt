package com.umc.ttoklip.di

import com.umc.ttoklip.R
import com.umc.ttoklip.TtoklipApplication
import com.umc.ttoklip.data.api.HoneyTipApi
import com.umc.ttoklip.data.api.LoginApi
import com.umc.ttoklip.data.api.NewsApi
import com.umc.ttoklip.data.api.SearchApi
import com.umc.ttoklip.data.api.SignupApi
import com.umc.ttoklip.data.api.TestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val NETWORK_EXCEPTION_OFFLINE_CASE = "network status is offline"
    const val NETWORK_EXCEPTION_BODY_IS_NULL = "result body is null"

    @Provides
    @Singleton
    fun provideOKHttpClient (): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val authIntercepter=AuthIntercepter()

        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authIntercepter)
            .retryOnConnectionFailure(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(TtoklipApplication.getString(R.string.base_url))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    class AuthIntercepter:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response= with(chain) {
//            if(request().headers["Auth"]=="false"){
//                val newRequest = request().newBuilder()
//                    .removeHeader("Auth")
//                    .build()
//                return chain.proceed(newRequest)
//            }
            val token=("Bearer "+TtoklipApplication.prefs.getString("jwt",""))
            val newRequest=request().newBuilder()
                .addHeader("Authorization",token)
                .build()
            proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideTestApi(retrofit: Retrofit): TestApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideHoneyTipApi(retrofit: Retrofit): HoneyTipApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit): SearchApi {
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideLoginApi(retrofit: Retrofit): LoginApi{
        return retrofit.buildService()
    }

    @Provides
    @Singleton
    fun provideSignupApi(retrofit: Retrofit): SignupApi{
        return retrofit.buildService()
    }

    private inline fun <reified T> Retrofit.buildService(): T {
        return this.create(T::class.java)
    }
}