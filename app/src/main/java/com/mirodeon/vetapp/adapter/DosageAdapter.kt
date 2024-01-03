package com.mirodeon.vetapp.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mirodeon.vetapp.R
import com.mirodeon.vetapp.application.MyApp
import com.mirodeon.vetapp.databinding.CellDosageBinding
import com.mirodeon.vetapp.room.entity.DosageWithMethod

class DosageAdapter() :
    ListAdapter<DosageWithMethod, DosageAdapter.DosageViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DosageWithMethod>() {
            override fun areItemsTheSame(
                oldItem: DosageWithMethod,
                newItem: DosageWithMethod
            ): Boolean {
                return oldItem.dosage.dosageId == newItem.dosage.dosageId
            }

            override fun areContentsTheSame(
                oldItem: DosageWithMethod,
                newItem: DosageWithMethod
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DosageViewHolder {
        /*viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }*/
        return DosageViewHolder(
            CellDosageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DosageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DosageViewHolder(
        private var binding: CellDosageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(dosage: DosageWithMethod) {
            binding.txtName.text = dosage.dosage.name
            binding.btnFav.imageTintList =
                ColorStateList.valueOf(
                    if (dosage.dosage.isFav)
                        ContextCompat.getColor(
                            MyApp.instance,
                            R.color.red
                        )
                    else
                        ContextCompat.getColor(
                            MyApp.instance,
                            R.color.white
                        )
                )
            binding.btnFav.setOnClickListener {

            }
        }
    }
}