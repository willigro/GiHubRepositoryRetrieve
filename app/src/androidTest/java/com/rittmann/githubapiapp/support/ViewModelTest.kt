//package com.rittmann.githubapiapp.support
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import androidx.test.espresso.IdlingRegistry
//import androidx.test.espresso.IdlingResource
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.setMain
//import org.junit.Rule
//import org.junit.rules.TestRule
//import org.junit.rules.TestWatcher
//import org.junit.runner.Description
//import org.junit.runners.model.Statement
//
//@ExperimentalCoroutinesApi
//open class ViewModelTest {
//
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//
//    @get:Rule
//    var coroutinesTestRule = CoroutineTestRule()
//
//    @get:Rule
//    var rule2 = OkHttpIdlingResourceRule()
//}
//
//class OkHttpIdlingResourceRule : TestRule {
//
//    val resource: IdlingResource = OkHttp3IdlingResource.create("okhttp${System.currentTimeMillis()}", RemoteDataImp.mClient())
//
//    override fun apply(base: Statement, description: Description): Statement {
//        return object : Statement() {
//            override fun evaluate() {
//                IdlingRegistry.getInstance().register(resource)
//                base.evaluate()
//                IdlingRegistry.getInstance().unregister(resource)
//            }
//        }
//    }
//}
//
//@ExperimentalCoroutinesApi
//class CoroutineTestRule(val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()) : TestWatcher() {
//
//    val testDispatcherProvider = object : DispatcherProvider {
//        override fun default(): CoroutineDispatcher = testDispatcher
//        override fun io(): CoroutineDispatcher = testDispatcher
//        override fun main(): CoroutineDispatcher = testDispatcher
//        override fun unconfined(): CoroutineDispatcher = testDispatcher
//    }
//
//    override fun starting(description: Description?) {
//        super.starting(description)
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    override fun finished(description: Description?) {
//        super.finished(description)
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
//}