package com.rittmann.githubapiapp

import android.app.Application
import android.content.Context
import com.rittmann.githubapiapp.model.local.room.AppDatabase
import com.rittmann.githubapiapp.model.local.room.RepositoryDao
import com.rittmann.githubapiapp.model.remote.GitHubRepositoryApi
import com.rittmann.githubapiapp.repository.GitHubRepository
import com.rittmann.githubapiapp.repository.GitHubRepositoryImpl
import com.rittmann.githubapiapp.ui.list.MainViewModel
import com.rittmann.githubapiapp.ui.list.ViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.erased.*

fun Context.asApp() = this.applicationContext as GitHubApplication

class GitHubApplication : Application(), KodeinAware {

    var testModule: Kodein.Module? = null

    override val kodein = Kodein.lazy {
        bindRepositories()

        bindViewModelFactories()

        bindViewModels()

        bindDao()

        bindApi()

        testModule?.also {
            import(testModule!!, allowOverride = true)
        }
    }

    private fun Kodein.MainBuilder.bindDao() {
        bind<AppDatabase>() with eagerSingleton {
            AppDatabase.getDatabase(applicationContext)!!
        }
        bind<RepositoryDao>() with eagerSingleton {
            (instance() as AppDatabase).getRepositoryDao()
        }
    }

    private fun Kodein.MainBuilder.bindApi() {
        bind<GitHubRepositoryApi>() with singleton { GitHubRepositoryApi(applicationContext) }
    }

    private fun Kodein.MainBuilder.bindRepositories() {
        bind<GitHubRepository>() with singleton { GitHubRepositoryImpl(instance(), instance()) }
    }

    private fun Kodein.MainBuilder.bindViewModelFactories() {
//        bind() from provider { MainViewModelFactory(instance()) }
        bind() from provider { ViewModelFactory(instance()) }
    }

    private fun Kodein.MainBuilder.bindViewModels() {
        bind<MainViewModel>() with provider {
            MainViewModel(instance())
        }
    }
}