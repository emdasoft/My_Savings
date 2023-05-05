package com.emdasoft.mysavings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.emdasoft.mysavings.R
import com.emdasoft.mysavings.databinding.FragmentSpendBinding
import com.emdasoft.mysavings.domain.entity.CardItem


class SpendFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[SpendViewModel::class.java]
    }

    private var selectedCardItem: CardItem? = null

    private var _binding: FragmentSpendBinding? = null
    private val binding: FragmentSpendBinding
        get() = _binding ?: throw RuntimeException("FragmentSpendBinding = null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelObserve()

        setListeners()

    }

    private fun setListeners() {
        binding.autoCompleteCard.setOnItemClickListener { adapterView, _, i, _ ->
            selectedCardItem = adapterView.getItemAtPosition(i) as CardItem
        }

        binding.buttonSpend.setOnClickListener {
            viewModel.spendMoney(binding.teAmount.text?.toString(), selectedCardItem)
        }

        binding.teAmount.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetAmountError()
            }
        })

        binding.autoCompleteCard.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetChooseCardError()
            }
        })
    }

    private fun viewModelObserve() {
        viewModel.cardList.observe(viewLifecycleOwner) {
            val arrayAdapter = ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                it
            )
            binding.autoCompleteCard.setAdapter(arrayAdapter)
        }

        viewModel.shouldScreenClose.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        viewModel.showInputAmountError.observe(viewLifecycleOwner) {
            val message = getString(R.string.incorrect_amount_error)
            if (it) {
                binding.tilAmount.error = message
            } else {
                binding.tilAmount.error = null
            }
        }

        viewModel.showChooseCardError.observe(viewLifecycleOwner) {
            val message = getString(R.string.choose_card_error)
            if (it) {
                binding.tilCardToSpend.error = message
            } else {
                binding.tilCardToSpend.error = null
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SpendFragment()
    }

}