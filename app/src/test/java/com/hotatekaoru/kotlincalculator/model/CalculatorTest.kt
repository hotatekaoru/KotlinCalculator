package com.hotatekaoru.kotlincalculator.model

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class CalculatorTest {
    private lateinit var calculator: Calculator

    @Before
    fun setup() {
        calculator = Calculator()
    }

    @Test
    fun `Calculator() 四則演算がない場合、そのまま返却されること`() {
        val value = calculator.call("1")
        Assertions.assertEquals(1.0, value)
    }

    @Test
    fun `Calculator() 負数から始まり、四則演算がない場合、そのまま返却されること`() {
        val value = calculator.call("-1")
        Assertions.assertEquals(-1.0, value)
    }

    @Test
    fun `Calculator() 式の途中で掛け算がある場合、正しく計算されること`() {
        val value = calculator.call("1+2×3")
        Assertions.assertEquals(7.0, value)
    }

    @Test
    fun `Calculator() 式の途中で引き算・掛け算がある場合、正しく計算されること`() {
        val value = calculator.call("1-2×3")
        Assertions.assertEquals(-5.0, value)
    }

    @Test
    fun `Calculator() 式の途中で掛け算があり、かつ、先頭がマイナスから始まる場合、正しく計算されること`() {
        val value = calculator.call("-1+2×3")
        Assertions.assertEquals(5.0, value)
    }

    @Test
    fun `Calculator() 式の途中で0で割る式がある場合、正しく計算されること`() {
        val value = calculator.call("1÷0")
        Assertions.assertEquals(Double.POSITIVE_INFINITY, value)
    }
}
