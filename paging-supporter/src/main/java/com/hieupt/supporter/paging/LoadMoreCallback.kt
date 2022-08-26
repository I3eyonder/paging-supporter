package com.hieupt.supporter.paging

/**
 * Created by HieuPT on 7/27/2022.
 */
fun interface LoadMoreCallback {
    fun onLoadMore(currentItemCount: Int, loadMoreState: LoadMoreState)
}