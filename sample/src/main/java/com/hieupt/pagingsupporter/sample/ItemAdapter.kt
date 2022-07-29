package com.hieupt.pagingsupporter.sample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.pagingsupporter.sample.databinding.ItemNumberBinding

/**
 * Created by HieuPT on 7/27/2022.
 */
class ItemAdapter : ListAdapter<Int, ItemAdapter.ItemVH>(NumberDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH =
        LayoutInflater.from(parent.context).run {
            ItemVH(ItemNumberBinding.inflate(this, parent, false))
        }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemVH(private val viewBinding: ItemNumberBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        fun bind(num: Int) {
            viewBinding.apply {
                tvNumber.text = "$adapterPosition - $num"
            }
        }
    }

    object NumberDiffCallback : DiffUtil.ItemCallback<Int>() {

        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean = oldItem == newItem
    }
}