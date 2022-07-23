package com.example.android_imperative.di

import android.app.Application
import android.content.Context
import com.example.android_imperative.db.Appdatabase
import com.example.android_imperative.db.TVshowDao
import com.example.android_imperative.network.Server.IS_TESTER
import com.example.android_imperative.network.Server.getDevelopment
import com.example.android_imperative.network.Server.getProduction
import com.example.android_imperative.retrofit.TVShowsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    /**
    Retrofit Related
     */

    @Provides
    fun  server():String{
        if(IS_TESTER)return getDevelopment()
        return getProduction()
    }

    @Provides
    @Singleton
    fun retrofitClient():Retrofit{
        return Retrofit.Builder().baseUrl(server())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun tvShowService():TVShowsService{
        return retrofitClient().create(TVShowsService::class.java)
    }

    /**
     *Room related
     */

    @Provides
    @Singleton
    fun appdatabase(context: Application):Appdatabase{
        return Appdatabase.getAppDBInstance(context)
    }
    @Provides
    @Singleton
    fun tvShowdao(appdatabase: Appdatabase):TVshowDao{
        return appdatabase.getTVShowDao()
    }

}