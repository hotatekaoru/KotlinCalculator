package com.hotatekaoru.kotlincalculator.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.hotatekaoru.kotlincalculator.R
import com.hotatekaoru.kotlincalculator.databinding.ActivityCalculatorBinding
import com.hotatekaoru.kotlincalculator.viewmodel.CalculatorViewModel

class CalculatorActivity : AppCompatActivity() {

    private lateinit var viewModel: CalculatorViewModel
    private lateinit var binding: ActivityCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_calculator)
        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        binding.viewmodel = viewModel
    }
}
