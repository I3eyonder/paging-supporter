package com.hieupt.supporter.paging.listeners

/**
 * Created by HieuPT on 7/27/2022.
 */
fun interface OnLoadingChangedListener {
    /**
     * Called when [LoadMoreState][com.hieupt.supporter.paging.LoadMoreState] notify that loading changed
     */
    fun onLoadingChanged(isLoading: Boolean)
}