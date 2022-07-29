package com.hieupt.supporter.paging.scrollhandler

import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by HieuPT on 7/28/2022.
 */
internal object StaggeredLoadMoreScrollHandler : LoadMoreScrollHandler<StaggeredGridLayoutManager> {

    override fun firstVisiblePosition(layoutManager: StaggeredGridLayoutManager): Int =
        if (layoutManager.childCount > 0) {
            layoutManager.findFirstVisibleItemPositions(null)[0]
        } else {
            0
        }

    override fun lastVisiblePosition(layoutManager: StaggeredGridLayoutManager): Int =
        if (layoutManager.childCount > 0) {
            layoutManager.findLastVisibleItemPositions(null)[0]
        } else {
            0
        }

    override fun isScrollingCorrectDirection(
        layoutManager: StaggeredGridLayoutManager,
        dx: Int,
        dy: Int,
        isReverseScroll: Boolean
    ): Boolean = if (layoutManager.orientation == StaggeredGridLayoutManager.VERTICAL) {
        if (!isReverseScroll) {
            dy > 0
        } else {
            dy < 0
        }
    } else {
        if (!isReverseScroll) {
            dx > 0
        } else {
            dx < 0
        }
    }

    override fun shouldTriggerLoadMore(
        layoutManager: StaggeredGridLayoutManager,
        firstVisiblePosition: Int,
        lastVisiblePosition: Int,
        itemCount: Int,
        triggerThreshold: Int,
        isReverseScroll: Boolean
    ): Boolean = if (!isReverseScroll) {
        lastVisiblePosition + triggerThreshold >= itemCount
    } else {
        firstVisiblePosition - triggerThreshold <= 0
    }
}