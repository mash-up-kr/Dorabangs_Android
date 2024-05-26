package com.mashup.dorabangs.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesDorabangsRetrofit(
        jsonConverterFactory: Converter.Factory,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
//            .baseUrl("")
            .addConverterFactory(jsonConverterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesDorabangsOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        flipperOkhttpInterceptor: FlipperOkhttpInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(flipperOkhttpInterceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun providesConverterFactory(
        json: Json,
    ): Converter.Factory =
        json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun providesJsonBuilder(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun providesFlipperOkHttpPlugin(
        networkFlipperPlugin: NetworkFlipperPlugin
    ): FlipperOkhttpInterceptor =
        FlipperOkhttpInterceptor(networkFlipperPlugin)

    @Provides
    @Singleton
    fun providesNetworkFlipperPlugin() = NetworkFlipperPlugin()
}