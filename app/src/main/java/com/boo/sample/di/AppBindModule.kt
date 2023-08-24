package com.boo.sample.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class AppBindModule {
    /*@Binds
    @IntoSet
    abstract fun provideTokenInterceptor(tokenInterceptor: TokenInterceptor): Interceptor*/
}