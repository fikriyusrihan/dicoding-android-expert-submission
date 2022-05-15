package com.artworkspace.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.artworkspace.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Query("SELECT * FROM favorite_movies")
    fun getAllFavoriteMovies(): Flow<List<MovieEntity>>

    @Query("SELECT COUNT(id) FROM favorite_movies WHERE id = :id")
    fun isFavoriteMovie(id: Int): Flow<Boolean>

    @Query("DELETE FROM favorite_movies WHERE id = :id")
    suspend fun deleteMovieFromFavorite(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieAsFavorite(movieEntity: MovieEntity)

}