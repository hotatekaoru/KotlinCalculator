package com.hotatekaoru.kotlincalculator.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hotatekaoru.kotlincalculator.R
import kotlinx.android.synthetic.main.activity_calculator.*

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // editTextタップ時にキーボードを非表示にする
        formulaEditText.showSoftInputOnFocus = false
    }
}
