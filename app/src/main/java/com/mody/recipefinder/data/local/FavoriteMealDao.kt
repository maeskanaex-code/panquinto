package com.mody.recipefinder.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * DAO = Data Access Object. Room generates the implementation at compile time.
 *
 * Why Flow<List<...>> for reads: the UI observes changes automatically.
 * When we insert or delete, Compose recomposes with the new list without
 * any manual refresh. This is the standard Room + Compose pattern.
 *
 * Suspend functions for writes: they run on a background thread and
 * return when the operation completes.
 */
@Dao
interface FavoriteMealDao {

    @Query("SELECT * FROM favorite_meals ORDER BY name ASC")
    fun observeAll(): Flow<List<FavoriteMealEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE id = :id)")
    fun observeIsFavorite(id: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: FavoriteMealEntity)

    @Delete
    suspend fun delete(meal: FavoriteMealEntity)

    @Query("DELETE FROM favorite_meals WHERE id = :id")
    suspend fun deleteById(id: String)
}
