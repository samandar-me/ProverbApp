package com.sdk.proverbsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.databinding.ItemLayoutBinding
import com.sdk.proverbsapp.manager.SharedPrefManager
import com.sdk.proverbsapp.model.Proverb
import com.sdk.proverbsapp.util.Utils

class ProverbAdapter(
    private val context: Context,
    private var proverbList: List<Proverb>,
    private val onClick: (Proverb) -> Unit
) :
    RecyclerView.Adapter<ProverbAdapter.ProverbViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProverbViewHolder {
        return ProverbViewHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProverbViewHolder, position: Int) {
        val item = proverbList[position]

        holder.binding.apply {
            textView.text = item.title
            textNumber.text = position.plus(1).toString()

            linearLayout.setBackgroundColor(ContextCompat.getColor(context, getColor()))
        }
        holder.itemView.setOnClickListener {
            Utils.pos = position
            onClick(item)
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

    inner class ProverbViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}