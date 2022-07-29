package com.hieupt.supporter.paging

import com.hieupt.supporter.paging.listeners.OnHasMoreDataChangedListener
import com.hieupt.supporter.paging.listeners.OnLoadingChangedListener

/**
 * Created by HieuPT on 7/27/2022.
 */
class LoadMoreState(
    private val onLoadingChangedListener: OnLoadingChangedListener?,
    private val onHasMoreDataChangedListener: OnHasMoreDataChangedListener?
) {

    var isLoading: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                onLoadingChangedListener?.onLoadingChanged(value)
            }
        }

    var hasMoreData: Boolean = true
        set(value) {
            if (field != value) {
                field = value
                onHasMoreDataChangedListener?.onHasMoreDataChanged(value)
            }
        }

    var nextToken: Any? = null
}