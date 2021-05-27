package com.rittmann.githubapiapp.ui.list

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.IdlingRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.rittmann.androidtools.log.log
import com.rittmann.githubapiapp.R
import com.rittmann.githubapiapp.model.basic.Repository
import com.rittmann.githubapiapp.model.local.room.AppDatabase
import com.rittmann.githubapiapp.model.mock.Mock
import com.rittmann.githubapiapp.support.ActivityTest
import com.rittmann.githubapiapp.support.ExpressoUtil.checkSizeFromRecycler
import com.rittmann.githubapiapp.support.ExpressoUtil.putValue
import com.rittmann.githubapiapp.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest : ActivityTest() {

    lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun start() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun finish() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
        scenario.close()
    }

    @Test
    fun showTheEmptyList_WhenTheScreenOpen_Offline() {
        /*
        * Open screen
        * */
        scenario = getActivity()

        /*
        * Test
        * */
        checkSizeFromRecycler(R.id.recycler, 0)
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

        /*
        * Open screen
        * */
        scenario = getActivity()

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
        scenario = getActivity()

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
}