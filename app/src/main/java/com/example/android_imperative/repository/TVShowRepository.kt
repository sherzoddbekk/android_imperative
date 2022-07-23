package com.example.android_imperative.repository

import com.example.android_imperative.db.TVshowDao
import com.example.android_imperative.model.TvShow
import com.example.android_imperative.retrofit.TVShowsService
import javax.inject.Inject

class TVShowRepository @Inject constructor(
    private val tvShowsService: TVShowsService,
    private val tVshowDao: TVshowDao
) {

    /**
     * Retrofit Related
     */

    suspend fun apiTVShowPopular(page: Int) = tvShowsService.apiTVShowPopular(page)
    suspend fun apiTVShowDeatils(q: Int) = tvShowsService.apiTVShowDetails(q)

    /**
     * Room Related
     */

    suspend fun getTVShowsFromDB() = tVshowDao.getTVShowFromDB()
    suspend fun insertTvShowToDb(tvShow: TvShow) = tVshowDao.insertTVShowDB(tvShow)
    suspend fun deleteTvShowsFromDB() = tVshowDao.deleteTvShowsFromDb()
}