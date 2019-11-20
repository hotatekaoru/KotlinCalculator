package com.hotatekaoru.kotlincalculator.enums

enum class OperationType(val label: String) {
    PLUS("+") { override fun calc(x: Double, y: Double): Double = x + y },
    MINUS("-") { override fun calc(x: Double, y: Double): Double = x - y },
    MULTIPLE("×") { override fun calc(x: Double, y: Double): Double = x * y },
    DIVIDE("÷") { override fun calc(x: Double, y: Double): Double = x / y };

    companion object {
        fun isPlusOrMinus(enum: OperationType): Boolean {
            return PLUS == enum || MINUS == enum
        }
    }

    abstract fun calc(x: Double, y: Double): Double
}
