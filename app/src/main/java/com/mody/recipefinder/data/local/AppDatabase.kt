package com.mody.recipefinder.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * The Room database.
 *
 * version = 1 — bump this whenever you change the schema.
 * After a version bump you must either provide a Migration or set
 * fallbackToDestructiveMigration() (which wipes data). For an MVP,
 * we haven't added a Migration yet because we're still on v1.
 */
@Database(
    entities = [FavoriteMealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoriteMealDao(): FavoriteMealDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Singleton accessor. Double-checked locking ensures one instance
         * even if two threads call getInstance() at the same moment.
         *
         * @Volatile on INSTANCE guarantees visibility of writes across threads.
         * A Room database is expensive to create; you must never have two.
         */
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_finder.db"
                ).build().also { INSTANCE = it }
            }
    }
}
