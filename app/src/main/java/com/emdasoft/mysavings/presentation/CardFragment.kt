package com.emdasoft.mysavings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.emdasoft.mysavings.R
import com.emdasoft.mysavings.databinding.FragmentCardBinding
import com.emdasoft.mysavings.domain.entity.Category
import com.emdasoft.mysavings.domain.entity.Currency

class CardFragment : Fragment() {

    private var _binding: FragmentCardBinding? = null
    private val binding: FragmentCardBinding
        get() = _binding ?: throw RuntimeException("FragmentAddCardBinding = null")

    private val viewModel by lazy {
        ViewModelProvider(this)[CardItemViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTextChangedListeners()

        viewModelObserve()

        bindAutoCompletes()

        with(binding) {
            saveButton.setOnClickListener {
                viewModel.addCard(
                    etTitle.text?.toString(),
                    etCount.text?.toString(),
                    autoCompleteCurrency.text?.toString(),
                    autoCompleteCategory.text?.toString()
                )
            }
        }
    }

    private fun bindAutoCompletes() {
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            Currency.values()
        )
        val arrayAdapter2 = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_item,
            Category.values()
        )

        binding.apply {

            autoCompleteCurrency.setAdapter(arrayAdapter)
            autoCompleteCategory.setAdapter(arrayAdapter2)

        }
    }

    private fun setTextChangedListeners() {
        binding.etTitle.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputTitleError()
            }
        })

        binding.etCount.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputAmountError()
            }
        })

        binding.autoCompleteCurrency.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputCurrencyError()
            }
        })

        binding.autoCompleteCategory.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetInputCategoryError()
            }
        })
    }

    private fun viewModelObserve() {

        viewModel.shouldScreenClose.observe(viewLifecycleOwner) {
            if (it) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

        viewModel.showInputCurrencyError.observe(viewLifecycleOwner) {
            val message = "Please, choose currency"
            if (it) {
                binding.chooseCurrency.error = message
            } else {
                binding.chooseCurrency.error = null
            }
        }

        viewModel.showInputCategoryError.observe(viewLifecycleOwner) {
            val message = "Please, choose category"
            if (it) {
                binding.chooseCategory.error = message
            } else {
                binding.chooseCategory.error = null
            }
        }


        viewModel.showInputTitleError.observe(viewLifecycleOwner) {
            val message = "Incorrect Title"
            if (it) {
                binding.tilTitle.error = message
            } else {
                binding.tilTitle.error = null
            }
        }

        viewModel.showInputAmountError.observe(viewLifecycleOwner) {
            val message = "Incorrect Amount"
            if (it) {
                binding.tilCount.error = message
            } else {
                binding.tilCount.error = null
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = CardFragment()
    }
}