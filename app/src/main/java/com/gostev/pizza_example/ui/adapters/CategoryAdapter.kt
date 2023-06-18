package com.gostev.pizza_example.ui.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gostev.pizza_example.R
import com.gostev.pizza_example.databinding.CategoryItemBinding
import com.gostev.pizza_example.ui.data.CategoryViewItem


class CategoryAdapter(
    val clickListener: (CategoryViewItem) -> Unit,
) : ListAdapter<CategoryViewItem, CategoryAdapter.CategoryViewHolder>(CategoryDiffUtilCallback()) {

    var selectedPosition = 0

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryAdapter.CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(CategoryItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: CategoryAdapter.CategoryViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

    override fun getItem(position: Int): CategoryViewItem {
        return super.getItem(position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSelectItem(pos: Int) {
        selectedPosition = pos
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(
        val binding: CategoryItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryViewItem, position: Int) {
            if (selectedPosition == position) {
                binding.categoryName.setTextColor(Color.parseColor("#FD3A69"))
                binding.categoryName.setBackgroundResource(R.color.selected_background)
                binding.categoryCard.cardElevation = 0f

            } else {
                binding.categoryName.setTextColor(Color.parseColor("#C3C4C9"))
                binding.categoryName.setBackgroundResource(R.color.white)

                binding.categoryCard.cardElevation = 15f
            }

            binding.categoryName.text = item.name

            binding.root.setOnClickListener {
                setSelectItem(position)
                clickListener(item)
            }
        }
    }
}

class CategoryDiffUtilCallback : DiffUtil.ItemCallback<CategoryViewItem>() {

    override fun areItemsTheSame(oldItem: CategoryViewItem, newItem: CategoryViewItem): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: CategoryViewItem, newItem: CategoryViewItem): Boolean =
        oldItem == newItem
}
