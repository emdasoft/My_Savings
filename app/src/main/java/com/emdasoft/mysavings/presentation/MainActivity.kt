package com.emdasoft.mysavings.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.emdasoft.mysavings.R
import com.emdasoft.mysavings.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.tempButtonAdd.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, CardFragment.newInstanceAddMode())
                .addToBackStack(null)
                .commit()
        }
    }
}