package com.hieupt.supporter.paging

import androidx.recyclerview.widget.RecyclerView

/**
 * Created by HieuPT on 7/27/2022.
 */

fun RecyclerView.setupPagingSupporter(block: PagingSupporter.Builder.() -> Unit = {}): PagingSupporter.Builder =
    PagingSupporter.setupFor(this, block)

fun RecyclerView.installPagingSupporter(block: PagingSupporter.Builder.() -> Unit = {}): PagingSupporter =
    setupPagingSupporter(block).install()