package com.rittmann.githubapiapp.ui.list

import com.rittmann.androidtools.log.log

interface PageItem {
    fun getIdentification(): String
}

fun <T : PageItem> List<T>.different(list: List<T>): Boolean {
    "sizes: $size ${list.size}".log()
    if (size != list.size) return true

    for (i in 0 until size) {
        "ids: ${this[i].getIdentification()} ${list[i].getIdentification()}".log()
        if (this[i].getIdentification() != list[i].getIdentification()) return true
    }

    return false
}

data class PageInfo<T : PageItem>(
    var page: Int = DEFAULT_PAGE_INDEX,
    var size: Int = DEFAULT_PAGE_SIZE,
) {
    var isEndList: Boolean = false
    var list: List<T> = arrayListOf()
    var requestedPage: Int = DEFAULT_PAGE_INDEX

    class PageResult<T : PageItem>(
        var list: List<T> = arrayListOf(),
        var isEndList: Boolean = false
    )

    fun getResult(pageResult: PageResult<T>): List<T> {
        val load = (list as List<PageItem>).let {
            if (list.different(pageResult.list)) {
                list = pageResult.list
                true
            } else {
                this.isEndList = true
                false
            }
        }

        this.isEndList = pageResult.isEndList

        if (list.size < size)
            this.isEndList = true

        if (pageResult.list.isNotEmpty())
            page = requestedPage

        "load: $load".log()
        return if (load) list else arrayListOf()
    }

    fun getNextPage(next: Boolean = true): PageInfo<T> {
        val p = if (isEndList) page
        else {
            if (next) page + 1 else DEFAULT_PAGE_INDEX
        }

        return this.copy(page = p).also { info ->
            requestedPage = info.page
            "hasPageToLoad, trying load the page ${info.page}".log()
        }
    }

    fun reset() {
        requestedPage = DEFAULT_PAGE_INDEX
        page = DEFAULT_PAGE_INDEX
        list = arrayListOf()
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 10
    }
}