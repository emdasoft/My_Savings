package com.emdasoft.mysavings.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
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
                .replace(R.id.main_container, CardFragment.newInstanceAddMode())
                .addToBackStack(null)
                .commit()
        }

        binding.spendCard.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, SpendFragment.newInstance())
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
        binding.rvCardList.clipChildren = false
        PagerSnapHelper().attachToRecyclerView(binding.rvCardList)
    }

    private fun getDisplayMetrics(): DisplayMetrics {
        return resources.displayMetrics
    }

    private fun showNotification(title: String, id: Int) {
        val notificationManager =
            requireActivity().getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "channel_id",
                "channel_name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = NotificationCompat.Builder(requireContext(), "channel_id")
            .setContentTitle("Cards")
            .setContentText(title)
            .setSmallIcon(R.drawable.ic_add)
            .build()
        notificationManager.notify(id, notification)
    }


    override fun setOnClickListener(cardItem: CardItem) {
        showNotification(cardItem.title, cardItem.id)
    }

    override fun setOnRecycleClickListener(cardItem: CardItem) {
        viewModel.deleteItem(cardItem)
    }

    override fun setOnEditClickListener(cardItem: CardItem) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, CardFragment.newInstanceEditMode(cardItem.id))
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}