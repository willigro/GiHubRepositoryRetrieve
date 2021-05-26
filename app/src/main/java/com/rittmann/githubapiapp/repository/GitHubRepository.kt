package com.rittmann.githubapiapp.repository

import com.rittmann.githubapiapp.model.basic.Repository
import com.rittmann.githubapiapp.model.local.room.RepositoryDao
import com.rittmann.githubapiapp.model.remote.GitHubRepositoryApi
import com.rittmann.githubapiapp.ui.list.PageInfo
import retrofit2.HttpException

interface GitHubRepository {
    fun fetchRepositories(
        pageInfo: PageInfo<Repository>,
        name: String,
        callback: (PageInfo.PageResult<Repository>) -> Unit
    )

    suspend fun deleteAll()
}

class GitHubRepositoryImpl(
    private val gitHubRepositoryApi: GitHubRepositoryApi,
    private val repositoryDao: RepositoryDao
) : GitHubRepository {

    override fun fetchRepositories(
        pageInfo: PageInfo<Repository>,
        name: String,
        callback: (PageInfo.PageResult<Repository>) -> Unit
    ) {
        try {
            val result =
                gitHubRepositoryApi.getRepositories(name, pageInfo.page, pageInfo.size).execute()

            if (result.isSuccessful) {
                if (result.body() == null || result.body()?.items.isNullOrEmpty()) {
                    callback(PageInfo.PageResult(arrayListOf(), true))
                    return
                }

                val items = result.body()?.items!!
                items.forEach {
                    it.page = pageInfo.page
                }
                repositoryDao.insertAll(items)
                callback(PageInfo.PageResult(items))
            } else {
                loadFromDao(name, pageInfo, callback)
            }
        } catch (e: HttpException) {
            loadFromDao(name, pageInfo, callback)
        } catch (e: Throwable) {
            loadFromDao(name, pageInfo, callback)
        }
    }

    private fun loadFromDao(
        name: String,
        pageInfo: PageInfo<Repository>,
        callback: (PageInfo.PageResult<Repository>) -> Unit
    ) {
        repositoryDao.getAll(name, pageInfo.page).also { items ->
            callback(PageInfo.PageResult(items))
        }
    }

    override suspend fun deleteAll() {

    }
}