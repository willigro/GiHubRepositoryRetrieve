package com.rittmann.githubapiapp.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rittmann.androidtools.log.log
import com.rittmann.baselifecycle.base.BaseViewModel
import com.rittmann.baselifecycle.livedata.SingleLiveEvent
import com.rittmann.githubapiapp.model.basic.Repository
import com.rittmann.githubapiapp.repository.GitHubRepository
import com.rittmann.githubapiapp.utils.EspressoIdlingResource

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val injector: ViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return injector as T? ?: modelClass.newInstance()
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val repository: GitHubRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}

class MainViewModel constructor(
    val repository: GitHubRepository
) : BaseViewModel() {

    private var pageInfo: PageInfo<Repository> = PageInfo()

    private val _deleted: SingleLiveEvent<Void> = SingleLiveEvent()
    val deleted
        get() = _deleted

    private val _items: MutableLiveData<List<Repository>> = MutableLiveData()
    val repositories
        get() = _items

    init {
        "viewmodel init".log()
    }

    fun fetchRepositories(next: Boolean, name: String) {
        "${repositories.value}".log()
        name.log()
        showProgress()
        executeAsync {
            "increment".log()
            EspressoIdlingResource.increment()
            repository.fetchRepositories(pageInfo.getNextPage(next), name) { result ->
                executeMain {
                    repositories.value = pageInfo.getResult(result)
                    hideProgress()
                    EspressoIdlingResource.decrement()
                    "decrement".log()
                }
            }
        }
    }

    fun getPage() = pageInfo.requestedPage

    fun deleteAll() {
        executeAsync {
            repository.deleteAll()

            executeMain {
                _deleted.call()
            }
        }
    }
}