package com.boo.sample.di

import com.boo.sample.base.config.Config
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideClient() : OkHttpClient {
        val client = OkHttpClient.Builder().apply {
            connectTimeout(Config.INSTANCE.connectTimeOut, TimeUnit.MILLISECONDS)
            readTimeout(Config.INSTANCE.readTimeOut, TimeUnit.MILLISECONDS)
            writeTimeout(Config.INSTANCE.writeTimeOut, TimeUnit.MILLISECONDS)
        }

        val logging = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        client.addNetworkInterceptor(logging)

        return client.build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(Config.INSTANCE.apiUrl)
            client(client)
        }.apply {
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
}