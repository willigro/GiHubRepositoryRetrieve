package com.rittmann.githubapiapp.model.basic

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.rittmann.githubapiapp.model.local.room.TableRepository
import com.rittmann.githubapiapp.ui.list.PageItem
import java.io.Serializable

@Entity
class RepositoryResult(
    @PrimaryKey
    @SerializedName("items")
    val items: List<Repository>
)

@Entity(
    tableName = TableRepository.TABLE
)
class Repository(
    @PrimaryKey
    @SerializedName(TableRepository.ID)
    val id: String = "",

    @SerializedName(TableRepository.NAME)
    @ColumnInfo(name = TableRepository.NAME)
    val name: String? = "",

    @SerializedName(TableRepository.FULL_NAME)
    @ColumnInfo(name = TableRepository.FULL_NAME)
    val fullName: String? = "",

    @SerializedName(TableRepository.PRIVATE)
    @ColumnInfo(name = TableRepository.PRIVATE)
    val isPrivate: Boolean? = false,

    @SerializedName(TableRepository.DESCRIPTION)
    @ColumnInfo(name = TableRepository.DESCRIPTION)
    val description: String? = "",

    @SerializedName(TableRepository.CREATED_AT)
    @ColumnInfo(name = TableRepository.CREATED_AT)
    val createdAt: String? = "",

    @SerializedName(TableRepository.START_COUNT)
    @ColumnInfo(name = TableRepository.START_COUNT)
    val starts: Int? = 0,

    @SerializedName("owner")
    @Embedded
    val owner: RepositoryOwner? = null,
) : Serializable, PageItem {

    @Transient
    @ColumnInfo(name = TableRepository.PAGE)
    var page: Int = 0

    override fun getIdentification(): String = id

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Repository) return false
        return other.id == id && other.name == name && other.fullName == fullName && other.createdAt == createdAt
    }
}

class RepositoryOwner(
    @SerializedName("id")
    @ColumnInfo(name = TableRepository.ID_OWNER)
    @PrimaryKey
    val id: Int? = 0,

    @SerializedName(TableRepository.AVATAR_URL)
    @ColumnInfo(name = TableRepository.AVATAR_URL)
    val avatarUrl: String? = "",
) : Serializable
//
//    "id": 82128465,
//    "node_id": "MDEwOlJlcG9zaXRvcnk4MjEyODQ2NQ==",
//    "name": "Android",
//    "full_name": "open-android/Android",
//    "private": false,
//    "owner":
//    {
//        "login": "open-android",
//        "id": 23095877,
//        "node_id": "MDQ6VXNlcjIzMDk1ODc3",
//        "avatar_url": "https://avatars.githubusercontent.com/u/23095877?v=4",
//        "gravatar_id": "",
//        "url": "https://api.github.com/users/open-android",
//        "html_url": "https://github.com/open-android",
//        "followers_url": "https://api.github.com/users/open-android/followers",
//        "following_url": "https://api.github.com/users/open-android/following{/other_user}",
//        "gists_url": "https://api.github.com/users/open-android/gists{/gist_id}",
//        "starred_url": "https://api.github.com/users/open-android/starred{/owner}{/repo}",
//        "subscriptions_url": "https://api.github.com/users/open-android/subscriptions",
//        "organizations_url": "https://api.github.com/users/open-android/orgs",
//        "repos_url": "https://api.github.com/users/open-android/repos",
//        "events_url": "https://api.github.com/users/open-android/events{/privacy}",
//        "received_events_url": "https://api.github.com/users/open-android/received_events",
//        "type": "User",
//        "site_admin": false
//    },
//    "html_url": "https://github.com/open-android/Android",
//    "description": "GitHub上最火的Android开源项目,所有开源项目都有详细资料和配套视频",
//    "fork": false,
//    "url": "https://api.github.com/repos/open-android/Android",
//    "forks_url": "https://api.github.com/repos/open-android/Android/forks",
//    "keys_url": "https://api.github.com/repos/open-android/Android/keys{/key_id}",
//    "collaborators_url": "https://api.github.com/repos/open-android/Android/collaborators{/collaborator}",
//    "teams_url": "https://api.github.com/repos/open-android/Android/teams",
//    "hooks_url": "https://api.github.com/repos/open-android/Android/hooks",
//    "issue_events_url": "https://api.github.com/repos/open-android/Android/issues/events{/number}",
//    "events_url": "https://api.github.com/repos/open-android/Android/events",
//    "assignees_url": "https://api.github.com/repos/open-android/Android/assignees{/user}",
//    "branches_url": "https://api.github.com/repos/open-android/Android/branches{/branch}",
//    "tags_url": "https://api.github.com/repos/open-android/Android/tags",
//    "blobs_url": "https://api.github.com/repos/open-android/Android/git/blobs{/sha}",
//    "git_tags_url": "https://api.github.com/repos/open-android/Android/git/tags{/sha}",
//    "git_refs_url": "https://api.github.com/repos/open-android/Android/git/refs{/sha}",
//    "trees_url": "https://api.github.com/repos/open-android/Android/git/trees{/sha}",
//    "statuses_url": "https://api.github.com/repos/open-android/Android/statuses/{sha}",
//    "languages_url": "https://api.github.com/repos/open-android/Android/languages",
//    "stargazers_url": "https://api.github.com/repos/open-android/Android/stargazers",
//    "contributors_url": "https://api.github.com/repos/open-android/Android/contributors",
//    "subscribers_url": "https://api.github.com/repos/open-android/Android/subscribers",
//    "subscription_url": "https://api.github.com/repos/open-android/Android/subscription",
//    "commits_url": "https://api.github.com/repos/open-android/Android/commits{/sha}",
//    "git_commits_url": "https://api.github.com/repos/open-android/Android/git/commits{/sha}",
//    "comments_url": "https://api.github.com/repos/open-android/Android/comments{/number}",
//    "issue_comment_url": "https://api.github.com/repos/open-android/Android/issues/comments{/number}",
//    "contents_url": "https://api.github.com/repos/open-android/Android/contents/{+path}",
//    "compare_url": "https://api.github.com/repos/open-android/Android/compare/{base}...{head}",
//    "merges_url": "https://api.github.com/repos/open-android/Android/merges",
//    "archive_url": "https://api.github.com/repos/open-android/Android/{archive_format}{/ref}",
//    "downloads_url": "https://api.github.com/repos/open-android/Android/downloads",
//    "issues_url": "https://api.github.com/repos/open-android/Android/issues{/number}",
//    "pulls_url": "https://api.github.com/repos/open-android/Android/pulls{/number}",
//    "milestones_url": "https://api.github.com/repos/open-android/Android/milestones{/number}",
//    "notifications_url": "https://api.github.com/repos/open-android/Android/notifications{?since,all,participating}",
//    "labels_url": "https://api.github.com/repos/open-android/Android/labels{/name}",
//    "releases_url": "https://api.github.com/repos/open-android/Android/releases{/id}",
//    "deployments_url": "https://api.github.com/repos/open-android/Android/deployments",
//    "created_at": "2017-02-16T02:10:13Z",
//    "updated_at": "2021-05-20T15:46:48Z",
//    "pushed_at": "2021-01-31T08:07:23Z",
//    "git_url": "git://github.com/open-android/Android.git",
//    "ssh_url": "git@github.com:open-android/Android.git",
//    "clone_url": "https://github.com/open-android/Android.git",
//    "svn_url": "https://github.com/open-android/Android",
//    "homepage": "",
//    "size": 36,
//    "stargazers_count": 11398,
//    "watchers_count": 11398,
//    "language": null,
//    "has_issues": true,
//    "has_projects": true,
//    "has_downloads": true,
//    "has_wiki": true,
//    "has_pages": false,
//    "forks_count": 3159,
//    "mirror_url": null,
//    "archived": false,
//    "disabled": false,
//    "open_issues_count": 46,
//    "license": null,
//    "forks": 3159,
//    "open_issues": 46,
//    "watchers": 11398,
//    "default_branch": "master",
//    "score": 1
//}