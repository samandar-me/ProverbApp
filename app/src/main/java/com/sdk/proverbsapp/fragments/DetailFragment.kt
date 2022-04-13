package com.sdk.proverbsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        listManager = ListManager()
        proverbDatabase = ProverbDatabase.getInstance(requireContext())
        proverbAdapter = ProverbAdapterItem(requireContext(),listManager.getList(),
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}