package com.rittmann.githubapiapp.ui.list

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.rittmann.githubapiapp.R
import com.rittmann.githubapiapp.databinding.ActivityMainBinding
import com.rittmann.githubapiapp.extensions.viewModelProv
import com.rittmann.githubapiapp.ui.base.BaseBindingActivity
import com.rittmann.githubapiapp.ui.details.DetailsActivity
import com.rittmann.githubapiapp.utils.PagingUtils
import com.rittmann.widgets.extensions.gone
import com.rittmann.widgets.extensions.visible
import com.rittmann.widgets.progress.ProgressVisibleControl
import java.util.*

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    override var resIdViewReference: Int = R.id.swipe_refresh

    private lateinit var editTextSearch: EditTextSearch
    lateinit var repositoryAdapter: RepositoryAdapter

    private val viewModel: MainViewModel by viewModelProv()

    private var enableRefresh: MutableLiveData<Boolean> = MutableLiveData(true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        initObservers()
        fetchRepositories()
    }

    private fun initObservers() {
        viewModel.apply {
            repositories.observe(this@MainActivity, { items ->
                items?.also { repositoryAdapter.addData(items) }
                enableRefresh.value = true
            })

            deleted.observe(this@MainActivity, {
                fetchRepositories()
            })

            observeLoading(this)
        }
    }

    private fun initViews() {
        ProgressVisibleControl.customLayout(R.layout.custom_progress) {
            findViewById<View>(resIdViewReference).post {
                it.findViewById<TextView>(R.id.progress_description)?.text =
                    getString(R.string.loading_page).format(
                        viewModel.getPage()
                    )
            }
        }

        binding.apply {
            editTextSearch = EditTextSearch(editQuery) {
                runOnUiThread { txtInfo.gone() }
                recallRepositories(name = it)
            }
            editTextSearch.onStart = {
                txtInfo.visible()
            }

            repositoryAdapter = RepositoryAdapter(arrayListOf())
            repositoryAdapter.subscribeToItemClick { _, _, repository ->
                startActivity(
                    DetailsActivity.getIntent(this@MainActivity, repository)
                )
            }

            recycler.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = repositoryAdapter

                PagingUtils.setUpPaging(
                    scroll = this,
                    enableRefresh = enableRefresh,
                    refreshList = ::reloadTransactionsWithPaging
                )
            }

            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                recallRepositories()
            }
        }
    }

    private fun fetchRepositories(
        next: Boolean = false,
        name: String = binding.editQuery.text.toString()
    ) {
        if (name.isNotEmpty()) {
            enableRefresh.postValue(false)
            viewModel.fetchRepositories(next, name)
        }
    }


    private fun reloadTransactionsWithPaging() {
        fetchRepositories(true)
    }

    private fun recallRepositories(name: String = binding.editQuery.text.toString()) {
        // clear
        runOnUiThread { repositoryAdapter.clear() }
        fetchRepositories(name = name)
    }
}