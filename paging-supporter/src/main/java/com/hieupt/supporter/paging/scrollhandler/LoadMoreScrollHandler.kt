package com.hieupt.supporter.paging.scrollhandler

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 7/28/2022.
 */
interface LoadMoreScrollHandler<in M : RecyclerView.LayoutManager> {

    fun isOkToLoadMore(
        layoutManager: M,
        dx: Int,
        dy: Int,
        triggerThreshold: Int,
        isReverseScroll: Boolean
    ): Boolean =
        isScrollingCorrectDirection(
            layoutManager,
            dx,
            dy,
            isReverseScroll
        ) && shouldTriggerLoadMore(
            layoutManager,
            firstVisiblePosition(layoutManager),
            lastVisiblePosition(layoutManager),
            getItemCount(layoutManager),
            triggerThreshold,
            isReverseScroll
        )

    /**
     * Get current item count of the adapter
     * @param layoutManager current layout manager
     * @return current item count of the adapter
     */
    fun getItemCount(layoutManager: M): Int = layoutManager.itemCount

    /**
     * Get adapter position's of the first visible item
     * @param layoutManager current layout manager
     * @return adapter position's of the first visible item
     */
    fun firstVisiblePosition(layoutManager: M): Int

    /**
     * Get adapter position's of the last visible item
     * @param layoutManager current layout manager
     * @return adapter position's of the last visible item
     */
    fun lastVisiblePosition(layoutManager: M): Int

    /**
     * Indicate that current scroll direction is desired direction to trigger load more
     */
    fun isScrollingCorrectDirection(
        layoutManager: M,
        dx: Int,
        dy: Int,
        isReverseScroll: Boolean
    ): Boolean

    /**
     * Indicate that load more should be trigger or not based on conditions
     * @param layoutManager current layout manager
     * @param firstVisiblePosition adapter position's of the first visible item. Returned from [firstVisiblePosition()][LoadMoreScrollHandler.firstVisiblePosition]
     * @param lastVisiblePosition adapter position's of the last visible item. Returned from [firstVisiblePosition()][LoadMoreScrollHandler.lastVisiblePosition]
     * @param itemCount current item count of the adapter. Returned from [getItemCount()][LoadMoreScrollHandler.getItemCount]
     * @param triggerThreshold offset from the end of the scroll at which the load more event needs to be triggered.
     * @param isReverseScroll indicate scroll direction
     * @return true if load more should trigger, false otherwise.
     */
    fun shouldTriggerLoadMore(
        layoutManager: M,
        firstVisiblePosition: Int,
        lastVisiblePosition: Int,
        itemCount: Int,
        triggerThreshold: Int,
        isReverseScroll: Boolean
    ): Boolean
}