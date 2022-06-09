package com.sdk.proverbsapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.databinding.ItemLayout2Binding
import com.sdk.proverbsapp.manager.SharedPrefManager
import com.sdk.proverbsapp.model.Proverb

@SuppressLint("NotifyDataSetChanged")
class ProverbAdapterItem(
    private val context: Context,
    private var proverbList: ArrayList<Proverb>,
    private val onClickSave: (Proverb) -> Unit,
    private val onClickCopy: (Proverb) -> Unit
) :
    RecyclerView.Adapter<ProverbAdapterItem.ProverbViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProverbViewHolder {
        return ProverbViewHolder(
            ItemLayout2Binding.inflate(
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
            }
            imgFavorite.setOnClickListener {
                onClickSave(item)
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

    inner class ProverbViewHolder(val binding: ItemLayout2Binding) :
        RecyclerView.ViewHolder(binding.root)

    fun filterList(filteredList: ArrayList<Proverb>) {
        proverbList = filteredList
        notifyDataSetChanged()
    }
}