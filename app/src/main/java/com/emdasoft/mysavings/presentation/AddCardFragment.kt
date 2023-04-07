package com.emdasoft.mysavings.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.emdasoft.mysavings.databinding.FragmentAddCardBinding

class AddCardFragment : Fragment() {

    private var _binding: FragmentAddCardBinding? = null
    private val binding: FragmentAddCardBinding
        get() = _binding ?: throw RuntimeException("FragmentAddCardBinding = null")

    private val viewModel by lazy {
        ViewModelProvider(this)[CardItemViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            saveButton.setOnClickListener {
                viewModel.addCard(etTitle.text.toString(), etCount.text.toString().toDouble())
                requireActivity().supportFragmentManager.popBackStack()
            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance() = AddCardFragment()
    }
}