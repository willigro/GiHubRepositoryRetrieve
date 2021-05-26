package com.rittmann.githubapiapp.model.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rittmann.githubapiapp.BuildConfig
import com.rittmann.githubapiapp.model.basic.Repository


@Database(
    version = 1,
    entities = [Repository::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getRepositoryDao(): RepositoryDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, BuildConfig.BASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE
        }
    }
}