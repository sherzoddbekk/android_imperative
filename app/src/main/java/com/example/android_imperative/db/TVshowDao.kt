package com.example.android_imperative.db

import androidx.room.*
import com.example.android_imperative.model.TvShow

@Dao
interface TVshowDao {
    @Query("SELECT * FROM tv_show")
    suspend fun getTVShowFromDB():List<TvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVShowDB(tvShow: TvShow)

    @Query("DELETE FROM tv_show")
    suspend fun deleteTvShowsFromDb()
}