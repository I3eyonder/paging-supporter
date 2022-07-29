package com.hieupt.pagingsupporter.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hieupt.pagingsupporter.sample.databinding.ActivityMainBinding
import com.hieupt.supporter.paging.LoadMoreState
import com.hieupt.supporter.paging.PagingSupporter
import com.hieupt.supporter.paging.setupPagingSupporter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private var pagingSupporter: PagingSupporter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        initActions()
        lifecycleScope.launch(Dispatchers.IO) {
            val items = getItems(40)
            withContext(Dispatchers.Main) {
                adapter.submitList(items)
            }
        }
    }

    private fun initActions() {
        viewBinding.apply {
            btnInstall.setOnClickListener {
                pagingSupporter = rvItems.setupPagingSupporter {
                    setLoadMoreCallback { _, state ->
                        loadMore(state)
                    }
                    setOnLoadingChangedListener {
                        runOnUiThread {
                            pbLoading.isVisible = it
                        }
                    }
                    setOnHasMoreDataChangedListener {

                    }
                }.install()
                    .also {
                        it.loadMoreState.nextToken = adapter.itemCount
                    }
            }
            btnUninstall.setOnClickListener {
                pagingSupporter?.uninstall()
                pagingSupporter = null
            }
        }
    }

    private fun initViews() {
        viewBinding.apply {
            rvItems.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ItemAdapter().also {
                    this@MainActivity.adapter = it
                }
            }
        }
    }

    private fun loadMore(loadMoreState: LoadMoreState) {
        lifecycleScope.launch(Dispatchers.IO) {
            loadMoreState.isLoading = true
            delay(2000)
            withContext(Dispatchers.Main) {
                val newItems =
                    adapter.currentList + getItems(nextToken = loadMoreState.nextToken as Int?)
                adapter.submitList(newItems) {
                    loadMoreState.isLoading = false
                }
                loadMoreState.nextToken = newItems.size
                if (newItems.size >= 200) {
                    loadMoreState.hasMoreData = false
                }
            }
        }
    }

    private fun getItems(itemCount: Int = 20, nextToken: Int? = null): List<Int> = buildList {
        val start = nextToken ?: 0
        repeat(itemCount) {
            add(start + it + 1)
        }
    }
}