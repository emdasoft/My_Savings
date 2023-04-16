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

    private var screenMode = UNKNOWN_SCREEN_MODE
    private var cardItemId = UNDEFINED_ID

    private var _binding: FragmentCardBinding? = null
    private val binding: FragmentCardBinding
        get() = _binding ?: throw RuntimeException("FragmentAddCardBinding = null")

    private val viewModel by lazy {
        ViewModelProvider(this)[CardItemViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
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

        launchRightMode()

        viewModelObserve()

        bindAutoCompletes()

        setTextChangedListeners()

    }

    private fun launchRightMode() {
        when (screenMode) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
    }

    private fun launchAddMode() {
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

    private fun launchEditMode() {
        viewModel.getCardItem(cardItemId)
        viewModel.cardItemLD.observe(viewLifecycleOwner) {
            with(binding) {
                etTitle.setText(it.title)
                etCount.setText(it.amount.toString())
            }
        }
        with(binding) {
            saveButton.setOnClickListener {
                viewModel.editCard(
                    etTitle.text?.toString(),
                    etCount.text?.toString(),
                    autoCompleteCurrency.text?.toString(),
                    autoCompleteCategory.text?.toString()
                )
            }
        }
    }

    private fun parseArgs() {
        if (requireArguments().containsKey(SCREEN_MODE)) {
            requireArguments().getString(SCREEN_MODE)?.let {
                screenMode = it
            }
        } else throw RuntimeException("Unknown screen mode")
        if (screenMode == EDIT_MODE) {
            cardItemId = requireArguments().getInt(ITEM_ID)
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

        private const val SCREEN_MODE = "screen_mode"
        private const val ITEM_ID = "item_id"
        private const val ADD_MODE = "add_mode"
        private const val EDIT_MODE = "edit_mode"
        private const val UNDEFINED_ID = -1
        private const val UNKNOWN_SCREEN_MODE = ""


        fun newInstanceAddMode(): CardFragment {
            return CardFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun newInstanceEditMode(cardItemId: Int): CardFragment {
            return CardFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, EDIT_MODE)
                    putInt(ITEM_ID, cardItemId)
                }
            }
        }
    }

}