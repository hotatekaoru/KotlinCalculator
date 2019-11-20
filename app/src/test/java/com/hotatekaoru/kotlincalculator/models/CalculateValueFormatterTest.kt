package com.hotatekaoru.kotlincalculator.models

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class CalculateValueFormatterTest {
    private lateinit var formatter: CalculateValueFormatter

    @Before
    fun setup() {
        formatter = CalculateValueFormatter()
    }

    @Test
    fun `引数で受け取った値が小数点以下が0の場合、小数点なしの文字列が返却されること`() {
        val value = formatter.call(2.0)
        Assertions.assertEquals("2", value)
    }

    @Test
    fun `引数で受け取った値が小数点以下がある場合、小数点ありの文字列が返却されること`() {
        val value = formatter.call(2.1)
        Assertions.assertEquals("2.1", value)
    }

    @Test
    fun `引数で受け取った値が小数点以下が11桁ある場合、小数点ありの文字列が10桁まで返却されること`() {
        val value = formatter.call(2.12345678911)
        Assertions.assertEquals("2.1234567891", value)
    }

    @Test
    fun `引数で受け取った値が小数点以下が11桁あり、10桁に切り捨てると小数点以下が0になる場合、小数点なしの文字列が返却されること`() {
        val value = formatter.call(2.00000000001)
        Assertions.assertEquals("2", value)
    }
}
