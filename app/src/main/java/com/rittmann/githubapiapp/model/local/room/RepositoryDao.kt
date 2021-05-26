package com.rittmann.githubapiapp.model.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rittmann.githubapiapp.model.basic.Repository

@Dao
interface RepositoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repositories: List<Repository>)

    @Query("SELECT * FROM ${TableRepository.TABLE}")
    fun getAll(): List<Repository>

    @Query("SELECT * FROM ${TableRepository.TABLE} WHERE ${TableRepository.NAME} LIKE :name AND ${TableRepository.PAGE} = :page")
    fun getAll(name: String, page: Int): List<Repository>

    @Query("DELETE FROM ${TableRepository.TABLE}")
    fun clearAll()
}

object TableRepository {
    const val TABLE = "tb_repository"
    const val ID = "id"
    const val NAME = "name"
    const val FULL_NAME = "full_name"
    const val PRIVATE = "private"
    const val DESCRIPTION = "description"
    const val CREATED_AT = "created_at"
    const val START_COUNT = "stargazers_count"
    const val ID_OWNER = "id_owner"
    const val AVATAR_URL = "avatar_url"
    const val PAGE = "page"
}