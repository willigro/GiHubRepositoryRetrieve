package com.rittmann.githubapiapp.model.mock

import com.rittmann.githubapiapp.model.basic.Repository
import com.rittmann.githubapiapp.model.basic.RepositoryOwner

object Mock {
    fun getRepository(
        id: String = "",
        name: String = "Repository test",
        fullName: String = "repository/Repository test",
        isPrivate: Boolean = false,
        description: String = "Description about repository test",
        createdAt: String = "2017-02-16T02:10:13Z",
        starts: Int = 100,
        owner: RepositoryOwner = RepositoryOwner(
            id = 1,
            avatarUrl = null
        ),
        page: Int = 1
    ) = Repository(
        id = id,
        name = name,
        fullName = fullName,
        isPrivate = isPrivate,
        description = description,
        createdAt = createdAt,
        starts = starts,
        owner = owner
    ).apply {
        this.page = page
    }
}