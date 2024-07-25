package com.mashup.dorabangs.data.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mashup.dorabangs.data.BuildConfig
import com.mashup.dorabangs.data.datasource.local.api.UserLocalDataSource
import com.mashup.dorabangs.data.utils.DoraInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
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
            .baseUrl(BuildConfig.SERVER_BASE_URL)
            .addConverterFactory(jsonConverterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesDorabangsOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        flipperOkhttpInterceptor: FlipperOkhttpInterceptor,
        userLocalDataSource: UserLocalDataSource,
        json: Json,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(DoraInterceptor(json))
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(flipperOkhttpInterceptor)
            addInterceptor(
                Interceptor { chain ->
                    val token = runBlocking {
                        runCatching { userLocalDataSource.getUserAccessToken().first() }.getOrDefault("")
                    }
                    val request = chain.request().newBuilder()
                        .addHeader(AUTHORIZATION, "Bearer $token")
                        .build()

                    chain.proceed(request)
                },
            )
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Singleton
    fun providesConverterFactory(json: Json): Converter.Factory = json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    fun providesJsonBuilder(): Json =
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

    @Provides
    @Singleton
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun providesFlipperOkHttpPlugin(networkFlipperPlugin: NetworkFlipperPlugin): FlipperOkhttpInterceptor =
        FlipperOkhttpInterceptor(networkFlipperPlugin, true)

    @Provides
    @Singleton
    fun providesNetworkFlipperPlugin() = NetworkFlipperPlugin()

    private const val AUTHORIZATION = "Authorization"
}
