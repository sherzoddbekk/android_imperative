package com.example.android_imperative.retrofit

import com.example.android_imperative.model.TVShowDetails
import com.example.android_imperative.model.TVShowPopular
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TVShowsService {

    @GET("api/most-popular")
    suspend fun apiTVShowPopular(@Query("page") page:Int):Response<TVShowPopular>

    @GET("api/show-details")
    suspend fun apiTVShowDetails(@Query("q") q:Int):Response<TVShowDetails>
}