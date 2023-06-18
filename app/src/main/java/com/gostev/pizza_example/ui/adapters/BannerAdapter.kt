package com.gostev.pizza_example.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gostev.pizza_example.databinding.BannerItemBinding
import com.gostev.pizza_example.ui.data.BannerViewItem


class BannerAdapter() :
    ListAdapter<BannerViewItem, BannerAdapter.BannerViewHolder>(BannerDiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BannerAdapter.BannerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return BannerViewHolder(BannerItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: BannerAdapter.BannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class BannerViewHolder(
        val binding: BannerItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BannerViewItem) {
            binding.image.setImageResource(item.image)
        }
    }
}

class BannerDiffUtilCallback : DiffUtil.ItemCallback<BannerViewItem>() {

    override fun areItemsTheSame(oldItem: BannerViewItem, newItem: BannerViewItem): Boolean = false

    override fun areContentsTheSame(oldItem: BannerViewItem, newItem: BannerViewItem): Boolean =
        false
}
