package com.hieupt.supporter.paging.scrollhandler

import androidx.recyclerview.widget.LinearLayoutManager

/**
 * Created by HieuPT on 7/28/2022.
 */
internal object LinearLoadMoreScrollHandler : LoadMoreScrollHandler<LinearLayoutManager> {

    override fun firstVisiblePosition(layoutManager: LinearLayoutManager): Int =
        layoutManager.findFirstVisibleItemPosition()

    override fun lastVisiblePosition(layoutManager: LinearLayoutManager): Int =
        layoutManager.findLastVisibleItemPosition()

    override fun isScrollingCorrectDirection(
        layoutManager: LinearLayoutManager,
        dx: Int,
        dy: Int,
        isReverseScroll: Boolean
    ): Boolean =
        if (layoutManager.orientation == LinearLayoutManager.VERTICAL) {
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
        layoutManager: LinearLayoutManager,
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