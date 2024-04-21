package com.dicoding.github.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding.github.data.local.entity.Favorite

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from favorite")
    fun getALlFavorites() : LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<Favorite>
}