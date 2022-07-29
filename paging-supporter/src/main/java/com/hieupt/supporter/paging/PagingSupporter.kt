package com.hieupt.supporter.paging

import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.supporter.paging.internal.LoadMoreScrollListener
import com.hieupt.supporter.paging.listeners.OnHasMoreDataChangedListener
import com.hieupt.supporter.paging.listeners.OnLoadingChangedListener
import com.hieupt.supporter.paging.scrollhandler.LoadMoreScrollHandler

/**
 * Created by HieuPT on 7/27/2022.
 */
class PagingSupporter internal constructor(builder: Builder) {

    val loadMoreState =
        LoadMoreState(builder.onLoadingChangedListener, builder.onHasMoreDataChangedListener)

    val recyclerView = builder.recyclerView

    private val delegateScrollListeners = builder.delegateScrollListeners

    private val loadMoreScrollListener = LoadMoreScrollListener(
        builder.triggerThreshold,
        builder.loadMoreCallback,
        builder.loadMoreScrollHandler,
        builder.isReverseScroll,
        loadMoreState
    )

    init {
        recyclerView.apply {
            addOnScrollListener(loadMoreScrollListener)
            delegateScrollListeners.forEach {
                addOnScrollListener(it)
            }
        }
    }

    fun uninstall() {
        recyclerView.apply {
            removeOnScrollListener(loadMoreScrollListener)
            delegateScrollListeners.forEach {
                removeOnScrollListener(it)
            }
        }
    }

    class Builder(internal val recyclerView: RecyclerView) {

        internal val delegateScrollListeners = mutableSetOf<RecyclerView.OnScrollListener>()

        internal var triggerThreshold = DEFAULT_TRIGGER_THRESHOLD
        internal var isReverseScroll = false
        internal var onLoadingChangedListener: OnLoadingChangedListener? = null
        internal var onHasMoreDataChangedListener: OnHasMoreDataChangedListener? = null
        internal var loadMoreCallback: LoadMoreCallback? = null
        internal var loadMoreScrollHandler: LoadMoreScrollHandler<RecyclerView.LayoutManager>? =
            null

        /**
         * Set the offset from the end of the scroll at which the load more event needs to be triggered.
         */
        fun setTriggerThreshold(@IntRange(from = 1) triggerThreshold: Int) = apply {
            require(triggerThreshold > 0) {
                "Trigger threshold must be greater than 0"
            }
            this.triggerThreshold = triggerThreshold
        }

        fun setOnLoadingChangedListener(listener: OnLoadingChangedListener?) = apply {
            this.onLoadingChangedListener = listener
        }

        fun setOnHasMoreDataChangedListener(listener: OnHasMoreDataChangedListener?) = apply {
            this.onHasMoreDataChangedListener = listener
        }

        fun setLoadMoreCallback(listener: LoadMoreCallback?) = apply {
            this.loadMoreCallback = listener
        }

        fun addOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) = apply {
            delegateScrollListeners.add(onScrollListener)
        }

        fun removeOnScrollListener(onScrollListener: RecyclerView.OnScrollListener) = apply {
            delegateScrollListeners.remove(onScrollListener)
        }

        fun setLoadMoreScrollHandler(loadMoreScrollHandler: LoadMoreScrollHandler<*>?) =
            apply {
                this.loadMoreScrollHandler =
                    loadMoreScrollHandler as LoadMoreScrollHandler<RecyclerView.LayoutManager>
            }

        fun setReverseScroll(isReverseScroll: Boolean) = apply {
            this.isReverseScroll = isReverseScroll
        }

        fun install(): PagingSupporter = PagingSupporter(this)

        companion object {
            const val DEFAULT_TRIGGER_THRESHOLD = 5
        }

    }

    companion object {

        fun setupFor(
            recyclerView: RecyclerView,
            block: Builder.() -> Unit = {}
        ): Builder = Builder(recyclerView).apply(block)
    }
}