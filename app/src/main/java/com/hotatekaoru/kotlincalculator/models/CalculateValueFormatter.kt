package com.hotatekaoru.kotlincalculator.models

import com.hotatekaoru.kotlincalculator.extensions.toIntAndToString

class CalculateValueFormatter {
    fun call(double: Double): String {
        // 小数点以下は、有効桁数の誤差が出るため、最大10桁までの表示にする
        val roundedDouble = String.format("%.10f", double).toDouble()

        return roundedDouble.toIntAndToString()
    }
}
