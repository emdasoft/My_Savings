package com.emdasoft.mysavings.presentation

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.emdasoft.mysavings.R
import com.emdasoft.mysavings.databinding.FragmentMainBinding
import com.emdasoft.mysavings.domain.entity.CardItem


class MainFragment : Fragment(), CardListAdapter.SetOnClickListeners {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding = null")

    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var rvAdapter: CardListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()

        viewModelObserve()

        binding.tempButtonAdd.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, AddCardFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun viewModelObserve() {
        viewModel.cardList.observe(viewLifecycleOwner) {
            rvAdapter.cardsList = it
        }

        viewModel.balance.observe(viewLifecycleOwner) {
            val tmpText =
                String.format(resources.getString(R.string.total_balance_count), it.toString())
            binding.totalBalanceCount.text = tmpText
        }
    }

    private fun setupRecycler() {
        binding.rvCardList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCardList.setHasFixedSize(true)
        rvAdapter = CardListAdapter(this@MainFragment, getDisplayMetrics())
        binding.rvCardList.adapter = rvAdapter
        rvAdapter.setItemMargin(resources.getDimension(R.dimen.pager_margin).toInt())
        rvAdapter.updateDisplayMetrics()
        PagerSnapHelper().attachToRecyclerView(binding.rvCardList)
    }

    private fun getDisplayMetrics(): DisplayMetrics {
        return resources.displayMetrics
    }


    override fun setOnClickListener(cardItem: CardItem) {
        Toast.makeText(requireContext(), cardItem.title, Toast.LENGTH_SHORT).show()
    }

    override fun setOnRecycleClickListener(cardItem: CardItem) {
        viewModel.deleteItem(cardItem)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

}