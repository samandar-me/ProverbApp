package com.sdk.proverbsapp.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.sdk.proverbsapp.R
import com.sdk.proverbsapp.adapter.ProverbAdapterItem
import com.sdk.proverbsapp.database.ProverbDatabase
import com.sdk.proverbsapp.databinding.FragmentDetailBinding
import com.sdk.proverbsapp.manager.ListManager
import com.sdk.proverbsapp.manager.MyClipBoardManager
import com.sdk.proverbsapp.model.Proverb
import com.sdk.proverbsapp.util.Utils
import com.sdk.proverbsapp.util.toast


class DetailFragment : Fragment() {

    private lateinit var proverbAdapter: ProverbAdapterItem
    private lateinit var proverbDatabase: ProverbDatabase
    private lateinit var listManager: ListManager
    private var proverbList: ArrayList<Proverb> = ArrayList()
    private var mInterstitialAd: InterstitialAd? = null

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var isBack = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        loadInterstitalAd()
        admob()
        showInterstitialAd()
        admobBig()
    }

    private fun initViews(view: View) {

        listManager = ListManager()
        proverbDatabase = ProverbDatabase.getInstance(requireContext())
        proverbList = listManager.getList()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        proverbAdapter = ProverbAdapterItem(requireContext(), proverbList,
            onClickCopy = {
                copyProverb(it)
            }, onClickSave = {
                saveToDatabase(it)
            })
        setupRecyclerView()

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolBarText.text = Utils.title

        binding.apply {
            btnSearch.setOnClickListener {
                binding.edtSearch.requestFocus()
                isBack = !isBack
                linearProverb.isVisible = false
                linearSearch.isVisible = true
                showKeyboard(requireContext())
            }
        }
        editText()

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                hideKeyboard(requireActivity(), binding.edtSearch)
            }
        })
        binding.btnClose.setOnClickListener {
            binding.edtSearch.text?.clear()
        }
    }

    private fun admob() {
        MobileAds.initialize(requireContext()) {}
        val adRequest = AdRequest.Builder().build()
        binding.admob.loadAd(adRequest)
    }

    private fun editText() {
        binding.edtSearch.addTextChangedListener {
            proverbSearch(it.toString().trim().lowercase())
            binding.btnClose.isVisible = binding.edtSearch.text.toString().trim().isNotEmpty()
        }
    }

    private fun proverbSearch(text: String) {
        val filteredList: ArrayList<Proverb> = ArrayList()
        for (singlePro in proverbList) {
            if (singlePro.title.lowercase().contains(text)) {
                filteredList.add(singlePro)
            }
            binding.linearNotFound.isVisible = filteredList.isEmpty()
        }
        proverbAdapter.filterList(filteredList)
    }

    private fun setupRecyclerView() = binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = proverbAdapter
    }

    private fun copyProverb(proverb: Proverb) {
        val clipboardManager = MyClipBoardManager(requireContext())
        val text = proverb.title
        clipboardManager.copyClip(text)
        toast(getString(R.string.copy))
    }

    private fun saveToDatabase(proverb: Proverb) {
        proverbDatabase.mainDao().saveProverb(proverb)
        toast(getString(R.string.saved_data))
    }

    private fun showKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.edtSearch, 0)
    }

    private fun hideKeyboard(activity: Activity, viewToHide: View) {
        val imm = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewToHide.windowToken, 0)
    }

    private fun admobBig() {
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("TEST_ID_1", "TEST_ID_N"))
                .build()
        )
    }

    private fun loadInterstitalAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),
            resources.getString(R.string.interstitial_ad),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(p0: InterstitialAd) {
                    super.onAdLoaded(p0)
                    mInterstitialAd = p0
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    mInterstitialAd = null
                }
            })
    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    mInterstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                    mInterstitialAd = null
                    loadInterstitalAd()
                }

            }
            mInterstitialAd!!.show(requireActivity())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        isBack = !isBack
    }
}