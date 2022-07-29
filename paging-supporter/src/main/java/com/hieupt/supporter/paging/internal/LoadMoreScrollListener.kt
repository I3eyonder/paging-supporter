package com.hieupt.supporter.paging.internal

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.hieupt.supporter.paging.LoadMoreCallback
import com.hieupt.supporter.paging.LoadMoreState
import com.hieupt.supporter.paging.scrollhandler.GridLoadMoreScrollHandler
import com.hieupt.supporter.paging.scrollhandler.LinearLoadMoreScrollHandler
import com.hieupt.supporter.paging.scrollhandler.LoadMoreScrollHandler
import com.hieupt.supporter.paging.scrollhandler.StaggeredLoadMoreScrollHandler

/**
 * Created by HieuPT on 7/27/2022.
 */
internal class LoadMoreScrollListener(
    private val triggerThreshold: Int,
    private val loadMoreCallback: LoadMoreCallback?,
    private val loadMoreScrollHandler: LoadMoreScrollHandler<RecyclerView.LayoutManager>?,
    private val isReverseScroll: Boolean,
    private val loadMoreState: LoadMoreState
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        recyclerView.layoutManager?.let { layoutManager ->
            val isOkToLoadMore =
                loadMoreScrollHandler?.isOkToLoadMore(
                    layoutManager,
                    dx,
                    dy,
                    triggerThreshold,
                    isReverseScroll
                ) ?: when (layoutManager) {
                    is StaggeredGridLayoutManager -> {
                        StaggeredLoadMoreScrollHandler.isOkToLoadMore(
                            layoutManager,
                            dx,
                            dy,
                            triggerThreshold,
                            isReverseScroll
                        )
                    }
                    is GridLayoutManager -> {
                        GridLoadMoreScrollHandler.isOkToLoadMore(
                            layoutManager,
                            dx,
                            dy,
                            triggerThreshold,
                            isReverseScroll
                        )
                    }
                    is LinearLayoutManager -> {
                        LinearLoadMoreScrollHandler.isOkToLoadMore(
                            layoutManager,
                            dx,
                            dy,
                            triggerThreshold,
                            isReverseScroll
                        )
                    }
                    else -> throw IllegalArgumentException("Paging supporter is not support for ${layoutManager::class.simpleName} yet")
                }

            if (isOkToLoadMore) {
                if (!loadMoreState.isLoading && loadMoreState.hasMoreData) {
                    loadMoreCallback?.onLoadMore(layoutManager.itemCount, loadMoreState)
                }
            }
        }
    }
}