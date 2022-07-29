package com.hieupt.supporter.paging.scrollhandler

import androidx.recyclerview.widget.GridLayoutManager

/**
 * Created by HieuPT on 7/28/2022.
 */
internal object GridLoadMoreScrollHandler : LoadMoreScrollHandler<GridLayoutManager> {

    override fun firstVisiblePosition(layoutManager: GridLayoutManager): Int =
        layoutManager.findFirstVisibleItemPosition()

    override fun lastVisiblePosition(layoutManager: GridLayoutManager): Int =
        layoutManager.findLastVisibleItemPosition()

    override fun isScrollingCorrectDirection(
        layoutManager: GridLayoutManager,
        dx: Int,
        dy: Int,
        isReverseScroll: Boolean
    ): Boolean =
        if (layoutManager.orientation == GridLayoutManager.VERTICAL) {
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
        layoutManager: GridLayoutManager,
        firstVisiblePosition: Int,
        lastVisiblePosition: Int,
        itemCount: Int,
        triggerThreshold: Int,
        isReverseScroll: Boolean
    ): Boolean = if (!isReverseScroll) {
        lastVisiblePosition + triggerThreshold * layoutManager.spanCount >= itemCount
    } else {
        firstVisiblePosition - triggerThreshold * layoutManager.spanCount <= 0
    }
}