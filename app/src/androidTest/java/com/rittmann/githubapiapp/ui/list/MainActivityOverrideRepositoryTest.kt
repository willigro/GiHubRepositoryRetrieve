package com.rittmann.githubapiapp.ui.list

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
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
import org.junit.runner.RunWith
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.singleton

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityOverrideRepositoryTest : ActivityTest() {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun start() {
        testing.log("testing value on start ")
        AppDatabase.getDatabase(context)!!.getRepositoryDao().clearAll()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun finish() {
        testing.log("testing value in finish ")
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        scenario.close()
    }

    @Test
    fun showTheEmptyList_WhenThereAreNotItems_QueryAndroid_GettingFromMock() {
        /*
        * Mock Repository
        * */
        testing = TEST_ONE
        mockTheResult()

        /*
        * Open screen
        * */
        scenario = getActivity()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)

        putValue(R.id.edit_query, "Android")

        checkSizeFromRecycler(R.id.recycler, 0)
    }

    @Test
    fun showPageOne_WhenRequestTheFirstPage_QueryJava_GettingFromDao_Offline() {
        /*
        * Mock Repository
        * */
        testing = TEST_TWO
        mQuery = "Java"
        val items = arrayListOf<Repository>()

        for (i in 0 until PageInfo.DEFAULT_PAGE_SIZE) {
            items.add(Mock.getRepository(id = "$i 1", name = mQuery, page = 1))
        }

        for (i in 0 until PageInfo.DEFAULT_PAGE_SIZE) {
            items.add(Mock.getRepository(id = "$i 2", name = mQuery, page = 2))
        }

        AppDatabase.getDatabase(context)?.getRepositoryDao()?.insertAll(items)

        mockTheResult()

        /*
        * Open screen
        * */
        scenario = getActivity()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)

        putValue(R.id.edit_query, mQuery)

        waitRecyclerBePopulated()

        checkSizeFromRecycler(R.id.recycler, PageInfo.DEFAULT_PAGE_SIZE)
        "showPageOne_WhenRequestTheFirstPage_QueryJava_GettingFromDao_Offline finished well".log()
    }

    @Test
    fun showPageOne_WhenRequestTheFirstPage_QueryJava_GettingFromDao_Offline_3() {
        /*
        * Mock Repository
        * */
        testing = TEST_THREE
        mQuery = "Java"
        val items = arrayListOf<Repository>()

        for (i in 0 until 2) {
            items.add(Mock.getRepository(id = "$i 1", name = mQuery, page = 1))
        }

        for (i in 0 until 2) {
            items.add(Mock.getRepository(id = "$i 2", name = mQuery, page = 2))
        }

        AppDatabase.getDatabase(context)?.getRepositoryDao()?.insertAll(items)

        mockTheResult()

        /*
        * Open screen
        * */
        scenario = getActivity()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)

        putValue(R.id.edit_query, mQuery)

        waitRecyclerBePopulated()

        checkSizeFromRecycler(R.id.recycler, 2)
        "showPageOne_WhenRequestTheFirstPage_QueryJava_GettingFromDao_Offline finished well".log()
    }

    /**
     * Necessary also to handle the delay from the search bar
     * */
    private fun waitRecyclerBePopulated() {
        Thread.sleep(1000L)
    }

    private fun mockTheResult() {
        val repository = object : GitHubRepository {
            override fun fetchRepositories(
                pageInfo: PageInfo<Repository>,
                name: String,
                callback: (PageInfo.PageResult<Repository>) -> Unit
            ) {
                callback(
                    when (testing) {
                        TEST_ONE -> {
                            testing.log("here 1")
                            PageInfo.PageResult()
                        }
                        TEST_TWO -> {
                            testing.log("here 2")
                            val dao = AppDatabase.getDatabase(context)!!.getRepositoryDao()
                            PageInfo.PageResult(dao.getAll(mQuery, 1))
                        }
                        else -> {
                            testing.log("here 3")
                            val dao = AppDatabase.getDatabase(context)!!.getRepositoryDao()
                            PageInfo.PageResult(dao.getAll(mQuery, 1))
                        }
                    }
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

    companion object {
        var testing: Int = 0
        var mQuery: String = ""
        private const val TEST_ONE = 1
        private const val TEST_TWO = 2
        private const val TEST_THREE = 3
    }
}