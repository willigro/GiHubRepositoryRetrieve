package com.rittmann.githubapiapp.ui.list

import androidx.test.espresso.IdlingRegistry
import com.rittmann.androidtools.log.log
import com.rittmann.githubapiapp.R
import com.rittmann.githubapiapp.asApp
import com.rittmann.githubapiapp.model.basic.Repository
import com.rittmann.githubapiapp.model.local.room.AppDatabase
import com.rittmann.githubapiapp.model.mock.Mock
import com.rittmann.githubapiapp.repository.GitHubRepository
import com.rittmann.githubapiapp.support.ActivityTest
import com.rittmann.githubapiapp.support.ExpressoUtil.checkSizeFromRecycler
import com.rittmann.githubapiapp.support.ExpressoUtil.putValue
import com.rittmann.githubapiapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

class MainActivityTest : ActivityTest() {

    @Before
    fun start() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun finish() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun showTheEmptyList_WhenTheScreenOpen_Offline() {
        /*
        * Open screen
        * */
        getActivity<MainActivity>()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)
    }

    @Test
    fun showTheEmptyList_WhenThereAreNotItems_QueryAndroid_GettingFromMock() {
        /*
        * Mock Repository
        * */
        mockTheResult {
            PageInfo.PageResult()
        }

        /*
        * Open screen
        * */
        getActivity<MainActivity>()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)

        putValue(R.id.edit_query, "Android")

        checkSizeFromRecycler(R.id.recycler, 0)
    }

    @Test
    fun showTheFiveMockedItems_WhenLoadSomeMockedItems_QueryAndroid_GettingFromMock() {
        /*
        * Mock Repository
        * */
        mockTheResult {
            val items = arrayListOf<Repository>()

            for (i in 0 until 5) {
                items.add(Mock.getRepository())
            }

            PageInfo.PageResult(items)
        }

        /*
        * Open screen
        * */
        getActivity<MainActivity>()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)

        putValue(R.id.edit_query, "Android")

        waitRecyclerBePopulated()

        checkSizeFromRecycler(R.id.recycler, 5)
    }

    @Test
    fun showPageOne_WhenRequestTheFirstPage_QueryJava_GettingFromDao_Offline() {
        /*
        * Mock Repository
        * */
        val query = "Java"
        val dao = AppDatabase.getDatabase(context)?.getRepositoryDao()
        dao?.clearAll()
        val items = arrayListOf<Repository>()

        for (i in 0 until PageInfo.DEFAULT_PAGE_SIZE) {
            items.add(Mock.getRepository(id = "$i 1", name = query, page = 1))
        }

        for (i in 0 until PageInfo.DEFAULT_PAGE_SIZE) {
            items.add(Mock.getRepository(id = "$i 2", name = query, page = 2))
        }

        dao?.insertAll(items)

        mockTheResult {
            PageInfo.PageResult(dao?.getAll(query, 1) ?: arrayListOf())
        }

        /*
        * Open screen
        * */
        getActivity<MainActivity>()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)

        putValue(R.id.edit_query, query)
        waitRecyclerBePopulated()
        checkSizeFromRecycler(R.id.recycler, PageInfo.DEFAULT_PAGE_SIZE)
        "showPageOne_WhenRequestTheFirstPage_QueryJava_GettingFromDao_Offline finished well".log()
    }

    /**
     * Turn on the internet
     * */
    @Test
    fun showPageOne_WhenRequestTheFirstPage_QueryJava_GettingFromApi_Online() {
        /*
        * Open screen
        * */
        getActivity<MainActivity>()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)

        putValue(R.id.edit_query, "Java")

        waitRecyclerBePopulated()

        checkSizeFromRecycler(R.id.recycler, PageInfo.DEFAULT_PAGE_SIZE)
        "showPageOne_WhenRequestTheFirstPage_QueryJava_GettingFromApi_Online finished well".log()
    }

    /**
     * Necessary also to handle the delay from the search bar
     * */
    private fun waitRecyclerBePopulated() {
        Thread.sleep(1000L)
    }

    private fun mockTheResult(block: () -> PageInfo.PageResult<Repository>) {
        val repository = object : GitHubRepository {
            override fun fetchRepositories(
                pageInfo: PageInfo<Repository>,
                name: String,
                callback: (PageInfo.PageResult<Repository>) -> Unit
            ) {
                callback(
                    block()
                )
            }

            override suspend fun deleteAll() {
            }
        }

        val testModule = Kodein.Module(name = "test", allowSilentOverride = true) {
            bind<GitHubRepository>() with singleton { repository }
        }
        targetContext.asApp().testModule = testModule
    }
}