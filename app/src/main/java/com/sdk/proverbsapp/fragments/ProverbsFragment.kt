package com.sdk.proverbsapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.adapter.ProverbAdapter
import com.sdk.proverbsapp.databinding.FragmentProverbsBinding
import com.sdk.proverbsapp.util.BounceEdgeEffectFactory
import com.sdk.proverbsapp.util.ObjectLists.proverbList
import com.sdk.proverbsapp.util.Utils
import com.sdk.proverbsapp.util.toast

class ProverbsFragment : Fragment() {

    private lateinit var proverbAdapter: ProverbAdapter
    private var clickListener: ItemClickListener? = null

    private var _binding: FragmentProverbsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProverbsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        admob()
    }

    private fun initViews() {
        proverbAdapter = ProverbAdapter(requireContext(), proverbList()) {
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
            Utils.title = it.title
            clickListener?.clicked()
        }
        setupRecyclerView()
    }

    private fun admob() {
        MobileAds.initialize(requireContext()) {}
        val adRequest = AdRequest.Builder().build()
        binding.admob.loadAd(adRequest)
    }


    private fun setupRecyclerView() = binding.recyclerView.apply {
        setHasFixedSize(false)
        edgeEffectFactory = BounceEdgeEffectFactory()
        layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = proverbAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        proverbAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    interface ItemClickListener {
        fun clicked()
    }
}