package com.dicoding.github.data.repository
import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.github.data.local.entity.Favorite
import com.dicoding.github.data.local.room.FavoriteDao
import com.dicoding.github.data.local.room.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mFavoritesDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteRoomDatabase.getDatabase(application)
        mFavoritesDao = db.favoriteDao()
    }

    fun getAllFavorites() : LiveData<List<Favorite>> = mFavoritesDao.getALlFavorites()

    fun getFavoriteUserByUsername(username: String): LiveData<Favorite> = mFavoritesDao.getFavoriteUserByUsername(username)

    fun insert(favorite: Favorite){
        executorService.execute{
            mFavoritesDao.insert(favorite)
        }
    }

    fun delete(favorite: Favorite) {
        executorService.execute {  mFavoritesDao.delete(favorite) }
    }
}