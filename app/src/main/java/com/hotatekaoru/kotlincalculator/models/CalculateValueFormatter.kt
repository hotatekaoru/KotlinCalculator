package com.hotatekaoru.kotlincalculator.models

class CalculateValueFormatter {
    fun call(double: Double): String {
        // 小数点以下は、有効桁数の誤差が出るため、最大10桁までの表示にする
        val roundedDouble = String.format("%.10f", double).toDouble().toString()

        // 小数点以下がない場合は、toInt().toString()した値を返す
        return if (roundedDouble.toDouble().toInt().toDouble() == roundedDouble.toDouble()) {
            roundedDouble.toDouble().toInt().toString()
        } else {
            roundedDouble
        }
    }
}
