package com.mashup.dorabangs.data.di

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mashup.dorabangs.data.BuildConfig
import com.mashup.dorabangs.domain.usecase.user.GetUserAccessTokenUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Reusable
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
    @Reusable
    fun providesDorabangsOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        flipperOkhttpInterceptor: FlipperOkhttpInterceptor,
        getUserAccessTokenUseCase: GetUserAccessTokenUseCase,
    ): OkHttpClient =
        OkHttpClient.Builder().apply {
            addInterceptor(
                Interceptor { chain ->
                    val token = runBlocking {
                        runCatching { getUserAccessTokenUseCase() }.getOrDefault("")
                    }
                    val request = chain.request().newBuilder()
                        .addHeader(AUTHORIZATION, "Bearer $token")
                        .build()

                    chain.proceed(request)
                },
            )
            addInterceptor(httpLoggingInterceptor)
            addNetworkInterceptor(flipperOkhttpInterceptor)
            connectTimeout(20, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(20, TimeUnit.SECONDS)
        }.build()

    @Provides
    @Reusable
    fun providesConverterFactory(json: Json): Converter.Factory = json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Reusable
    fun providesJsonBuilder(): Json =
        Json {
            isLenient = true
            ignoreUnknownKeys = true
            coerceInputValues = true
        }

    @Provides
    @Reusable
    fun providesLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Reusable
    fun providesFlipperOkHttpPlugin(networkFlipperPlugin: NetworkFlipperPlugin): FlipperOkhttpInterceptor =
        FlipperOkhttpInterceptor(networkFlipperPlugin)

    @Provides
    @Reusable
    fun providesNetworkFlipperPlugin() = NetworkFlipperPlugin()

    private const val AUTHORIZATION = "Authorization"
}
