package com.rittmann.githubapiapp.model.remote

import android.content.Context
import com.rittmann.githubapiapp.model.basic.RepositoryResult
import com.rittmann.githubapiapp.model.remote.config.RestApiUtil
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubRepositoryApi {

    @GET("search/repositories")
    fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ): Call<RepositoryResult>

    companion object {
        operator fun invoke(
            context: Context
        ): GitHubRepositoryApi {
            return Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(RestApiUtil.getOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GitHubRepositoryApi::class.java)
        }
    }
}