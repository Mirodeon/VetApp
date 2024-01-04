package com.mirodeon.vetapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mirodeon.vetapp.databinding.CellMethodBinding
import com.mirodeon.vetapp.room.entity.Method

class MethodAdapter :
    ListAdapter<Method, MethodAdapter.MethodViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Method>() {
            override fun areItemsTheSame(
                oldItem: Method,
                newItem: Method
            ): Boolean {
                return oldItem.methodId == newItem.methodId
            }

            override fun areContentsTheSame(
                oldItem: Method,
                newItem: Method
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MethodViewHolder {
        return MethodViewHolder(
            CellMethodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MethodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MethodViewHolder(
        private var binding: CellMethodBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(method: Method) {
            binding.txtName.text = method.name.replaceFirstChar { it.uppercase() }
        }
    }
}