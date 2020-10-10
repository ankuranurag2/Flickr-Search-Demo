package dev.ananurag2.flickr.di

import dev.ananurag2.flickr.data.network.ApiService
import dev.ananurag2.flickr.utils.BASE_URL
import dev.ananurag2.flickr.utils.DEFAULT_TIME_OUT
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * created by ankur on 3/2/20
 */
val networkModule = module {
    factory { getLoggingInterceptor() }
    factory { getOkHttpClient(get()) }
    factory { getApiService(get()) }

    single { getRetrofit(get()) }
}

fun getOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient().newBuilder()
    .addInterceptor(interceptor)
    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
    .readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
    .writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
    .build()


fun getLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}


fun getRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    .client(okHttpClient)
    .build()


fun getApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)


