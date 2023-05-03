package com.emdasoft.mysavings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.emdasoft.mysavings.R
import com.emdasoft.mysavings.databinding.FragmentGetMoneyBinding


class GetMoneyFragment : Fragment() {

    private var selectedCardItem: Any? = null

    private var _binding: FragmentGetMoneyBinding? = null
    private val binding: FragmentGetMoneyBinding
        get() = _binding ?: throw RuntimeException("FragmentGetMoneyBinding = null")

    private val viewModel by lazy {
        ViewModelProvider(this)[GetMoneyViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGetMoneyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModelObserve()

        setListeners()

    }

    private fun setListeners() {
        binding.autoCompleteCard.setOnItemClickListener { adapterView, _, i, _ ->
            selectedCardItem = adapterView.getItemAtPosition(i)
        }

        binding.buttonGet.setOnClickListener {
            val amount = binding.teAmount.text?.toString()
            val sourceCard = selectedCardItem
            viewModel.getMoney(amount, sourceCard)
        }

        binding.teAmount.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                super.onTextChanged(p0, p1, p2, p3)
                viewModel.resetAmountError()
            }
        })

        binding.autoCompleteCard.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                super.onTextChanged(p0, p1, p2, p3)
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
            val message = "Incorrect amount!"
            if (it) {
                binding.tilAmount.error = message
            } else {
                binding.tilAmount.error = null
            }
        }

        viewModel.showChooseCardError.observe(viewLifecycleOwner) {
            val message = "Incorrect amount!"
            if (it) {
                binding.tilCardToGet.error = message
            } else {
                binding.tilCardToGet.error = null
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = GetMoneyFragment()
    }
}