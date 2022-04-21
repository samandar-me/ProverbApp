package com.sdk.proverbsapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.crowdfire.cfalertdialog.CFAlertDialog
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.adapter.FavoriteAdapter
import com.sdk.proverbsapp.database.ProverbDatabase
import com.sdk.proverbsapp.databinding.FragmentFavoriteBinding
import com.sdk.proverbsapp.manager.MyClipBoardManager
import com.sdk.proverbsapp.model.Proverb
import com.sdk.proverbsapp.util.toast

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private var proverbList: ArrayList<Proverb> = ArrayList()

    private lateinit var proverbDatabase: ProverbDatabase
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        admob()
    }

    private fun initViews() {
        proverbDatabase = ProverbDatabase.getInstance(requireContext())
        proverbList = proverbDatabase.mainDao().getAllProverbs() as ArrayList<Proverb>
        updateRecyclerView(proverbList)
        isCheck()
    }

    private fun updateRecyclerView(proverbList: ArrayList<Proverb>) {
        favoriteAdapter = FavoriteAdapter(requireContext(),proverbList,
            onClickCopy = {
                copyProverb(it)
            },
            onClickDelete = {
                deleteFromDatabase(it)
            }
        )
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteAdapter
        }
        binding.btnDeleteAll.setOnClickListener {
            showAlertDialog()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showAlertDialog() {
        val alertDialog = CFAlertDialog.Builder(requireContext())
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setTitle(getString(R.string.are_you))
            .addButton(
                getString(R.string.ha),
                -1,
                -1,
                CFAlertDialog.CFAlertActionStyle.NEGATIVE,
                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
            ) { dialog, _ ->
                proverbDatabase.mainDao().deleteAllProverbs()
                proverbList.clear()
                updateRecyclerView(proverbList)
                toast(getString(R.string.deleted_all))
                isCheck()
                dialog.dismiss()
            }
            .addButton(
                getString(R.string.yoq),
                -1,
                -1,
                CFAlertDialog.CFAlertActionStyle.POSITIVE,
                CFAlertDialog.CFAlertActionAlignment.JUSTIFIED
            ) { dialog, _ ->
                dialog.dismiss()
            }
        alertDialog.show()
    }

    private fun admob() {
        MobileAds.initialize(requireContext()) {}
        val adRequest = AdRequest.Builder().build()
        binding.admob.loadAd(adRequest)
    }

    private fun copyProverb(proverb: Proverb) {
        val clipboardManager = MyClipBoardManager(requireContext())
        val text = proverb.title
        clipboardManager.copyClip(text)
        toast(getString(R.string.copy))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteFromDatabase(proverb: Proverb) {
        proverbDatabase.mainDao().deleteProverb(proverb)
        proverbList.remove(proverb)
        favoriteAdapter.notifyDataSetChanged()
        isCheck()
        toast(getString(R.string.deleted))
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        favoriteAdapter.notifyDataSetChanged()
    }

    private fun isCheck() {
        binding.linearLayout.isVisible = proverbDatabase.mainDao().getAllProverbs().isEmpty()
        binding.btnDeleteAll.isVisible = proverbDatabase.mainDao().getAllProverbs().isNotEmpty()
                && proverbDatabase.mainDao().getAllProverbs().size > 1
    }
}