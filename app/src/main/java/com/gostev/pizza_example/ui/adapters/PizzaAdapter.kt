package com.gostev.pizza_example.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gostev.pizza_example.R
import com.gostev.pizza_example.databinding.PizzaItemBinding
import com.gostev.pizza_example.ui.data.PizzaViewItem


class PizzaAdapter(
    val clickListener: (PizzaViewItem) -> Unit,
) : ListAdapter<PizzaViewItem, PizzaAdapter.PizzaViewHolder>(PizzaDiffUtilCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PizzaAdapter.PizzaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PizzaViewHolder(PizzaItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: PizzaAdapter.PizzaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PizzaViewHolder(
        val binding: PizzaItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PizzaViewItem) {
            binding.name.text = item.name
            binding.description.text = item.description
            binding.price.text = item.price

            Glide.with(this.itemView)
                .load(item.photo)
                .placeholder(R.drawable.placeholder)
                .into(binding.photo)

            binding.root.setOnClickListener {
                clickListener(item)
            }
        }
    }
}

class PizzaDiffUtilCallback : DiffUtil.ItemCallback<PizzaViewItem>() {

    override fun areItemsTheSame(oldItem: PizzaViewItem, newItem: PizzaViewItem): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: PizzaViewItem, newItem: PizzaViewItem): Boolean =
        oldItem == newItem
}
