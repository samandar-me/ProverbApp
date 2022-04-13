package com.sdk.proverbsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.databinding.ItemFavoriteBinding
import com.sdk.proverbsapp.manager.SharedPrefManager
import com.sdk.proverbsapp.model.Proverb

class FavoriteAdapter(
    private val context: Context,
    private var proverbList: List<Proverb>,
    private val onClickDelete: (Proverb) -> Unit,
    private val onClickCopy: (Proverb) -> Unit
) :
    RecyclerView.Adapter<FavoriteAdapter.ProverbViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProverbViewHolder {
        return ProverbViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProverbViewHolder, position: Int) {
        val item = proverbList[position]

        holder.binding.apply {
            textTitle.text = item.title
            textNumber.text = position.plus(1).toString()

            cardView.setCardBackgroundColor(ContextCompat.getColor(context, getColor()))

            imgCopy.setOnClickListener {
                onClickCopy(item)
               // imgCopy.setBackgroundResource(R.drawable.ic_baseline_file_copy_24)
            }
            imgDelete.setOnClickListener {
                onClickDelete(item)
            }
        }
    }

    override fun getItemCount(): Int = proverbList.size

    private fun getColor(): Int {
        val sharedPrefManager = SharedPrefManager(context)
        return when (sharedPrefManager.getSavedColor()) {
            0 -> R.color.color1
            1 -> R.color.color2
            2 -> R.color.dark_blue
            3 -> R.color.color4
            4 -> R.color.light_gray3
            else -> R.color.color1
        }
    }

    inner class ProverbViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)
}