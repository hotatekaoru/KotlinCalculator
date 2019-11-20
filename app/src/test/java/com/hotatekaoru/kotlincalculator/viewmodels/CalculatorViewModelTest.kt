package com.hotatekaoru.kotlincalculator.viewmodels

import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions

class CalculatorViewModelTest {
    private lateinit var viewModel: CalculatorViewModel

    @Before
    fun setup() {
        viewModel = CalculatorViewModel()
        viewModel.calculating.set(false)
    }

    @Test
    fun `tapNumber() calculatingがtrueの場合、mainValueTextの末尾に入力された数字を加えること`() {
        viewModel.calculating.set(true)
        viewModel.mainValueText.set("1")
        viewModel.tapNumber(0)
        Assertions.assertEquals("10", viewModel.mainValueText.get())
        Assertions.assertTrue(viewModel.calculating.get())
    }

    @Test
    fun `tapNumber() calculatingがfalseの場合、mainValueTextが入力された数字に入れ替わること`() {
        viewModel.calculating.set(false)
        viewModel.mainValueText.set("1")
        viewModel.tapNumber(0)
        Assertions.assertEquals("0", viewModel.mainValueText.get())
        Assertions.assertTrue(viewModel.calculating.get())
    }

    @Test
    fun `tapDot() mainValueTextが空の場合、mainValueTextが小数点のみに更新されること`() {
        viewModel.mainValueText.set("")
        viewModel.tapDot()
        Assertions.assertEquals(".", viewModel.mainValueText.get())
    }

    @Test
    fun `tapDot() mainValueTextの末尾が小数点の場合、mainValueTextが更新されないこと`() {
        viewModel.mainValueText.set("1.")
        viewModel.tapDot()
        Assertions.assertEquals("1.", viewModel.mainValueText.get())
    }

    @Test
    fun `tapDot() mainValueTextの最後の数字列に小数点が含まれる場合、mainValueTextが更新されないこと`() {
        viewModel.mainValueText.set("1.2")
        viewModel.tapDot()
        Assertions.assertEquals("1.2", viewModel.mainValueText.get())
    }

    @Test
    fun `tapDot() mainValueTextの末尾が小数点でない場合、mainValueTextの末尾に小数点が追加されること`() {
        viewModel.mainValueText.set("1")
        viewModel.tapDot()
        Assertions.assertEquals("1.", viewModel.mainValueText.get())
        Assertions.assertTrue(viewModel.calculating.get())
    }

    @Test
    fun `tapPlus() mainValueTextが空の場合、mainValueTextが更新されないこと`() {
        viewModel.mainValueText.set("")
        viewModel.tapPlus()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }

    @Test
    fun `tapPlus() mainValueTextの末尾が小数点の場合、mainValueTextが更新されないこと`() {
        viewModel.mainValueText.set("1.")
        viewModel.tapPlus()
        Assertions.assertEquals("1.", viewModel.mainValueText.get())
    }

    @Test
    fun `tapPlus() mainValueTextが-のみの場合、mainValueTextが空に更新されること`() {
        viewModel.mainValueText.set("-")
        viewModel.tapPlus()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }

    @Test
    fun `tapPlus() mainValueTextの末尾が四則演算子の場合、末尾の四則演算子が+に入れ替わること`() {
        viewModel.mainValueText.set("1-")
        viewModel.tapPlus()
        Assertions.assertEquals("1+", viewModel.mainValueText.get())
        Assertions.assertTrue(viewModel.calculating.get())
    }

    @Test
    fun `tapPlus() 上記以外の場合、mainValueTextの末尾に+を追加こと`() {
        viewModel.mainValueText.set("1")
        viewModel.tapPlus()
        Assertions.assertEquals("1+", viewModel.mainValueText.get())
        Assertions.assertTrue(viewModel.calculating.get())
    }

    @Test
    fun `tapMinus() mainValueTextの末尾が小数点の場合、mainValueTextが更新されないこと`() {
        viewModel.mainValueText.set("1.")
        viewModel.tapMinus()
        Assertions.assertEquals("1.", viewModel.mainValueText.get())
    }

    @Test
    fun `tapMinus() mainValueTextの末尾が四則演算子の場合、末尾の四則演算子が-に入れ替わること`() {
        viewModel.mainValueText.set("1÷")
        viewModel.tapMinus()
        Assertions.assertEquals("1-", viewModel.mainValueText.get())
    }

    @Test
    fun `tapMinus() 上記以外の場合、mainValueTextの末尾に-が追加されること`() {
        viewModel.mainValueText.set("1")
        viewModel.tapMinus()
        Assertions.assertEquals("1-", viewModel.mainValueText.get())
        Assertions.assertTrue(viewModel.calculating.get())
    }

    @Test
    fun `tapMultiple() mainValueTextが空の場合、mainValueTextが更新されないこと`() {
        viewModel.mainValueText.set("")
        viewModel.tapMultiple()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }

    @Test
    fun `tapMultiple() mainValueTextの末尾が小数点の場合、mainValueTextが更新されないこと`() {
        viewModel.mainValueText.set("1.")
        viewModel.tapMultiple()
        Assertions.assertEquals("1.", viewModel.mainValueText.get())
    }

    @Test
    fun `tapMultiple() mainValueTextが-のみの場合、mainValueTextが空に更新されること`() {
        viewModel.mainValueText.set("-")
        viewModel.tapMultiple()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }

    @Test
    fun `tapMultiple() mainValueTextが四則演算子の場合、mainValueTextが空に更新されること`() {
        viewModel.mainValueText.set("-")
        viewModel.tapMultiple()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }

    @Test
    fun `tapMultiple() mainValueTextが空の場合、末尾の四則演算子が×に入れ替わること`() {
        viewModel.mainValueText.set("1+")
        viewModel.tapMultiple()
        Assertions.assertEquals("1×", viewModel.mainValueText.get())
    }

    @Test
    fun `tapMultiple() 上記以外の場合、mainValueTextの末尾に×が追加されること`() {
        viewModel.mainValueText.set("1")
        viewModel.tapMultiple()
        Assertions.assertEquals("1×", viewModel.mainValueText.get())
        Assertions.assertTrue(viewModel.calculating.get())
    }

    @Test
    fun `tapDivide() mainValueTextの末尾が小数点の場合、mainValueTextが更新されないこと`() {
        viewModel.mainValueText.set("1.")
        viewModel.tapDivide()
        Assertions.assertEquals("1.", viewModel.mainValueText.get())
    }

    @Test
    fun `tapDivide() mainValueTextが-のみの場合、mainValueTextが空に更新されること`() {
        viewModel.mainValueText.set("-")
        viewModel.tapDivide()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }

    @Test
    fun `tapDivide() mainValueTextが四則演算子の場合、mainValueTextが空に更新されること`() {
        viewModel.mainValueText.set("-")
        viewModel.tapDivide()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }

    @Test
    fun `tapDivide() mainValueTextが空の場合、末尾の四則演算子が÷に入れ替わること`() {
        viewModel.mainValueText.set("1-")
        viewModel.tapDivide()
        Assertions.assertEquals("1÷", viewModel.mainValueText.get())
    }

    @Test
    fun `tapDivide() 上記以外の場合、mainValueTextの末尾に÷が追加されること`() {
        viewModel.mainValueText.set("1")
        viewModel.tapDivide()
        Assertions.assertEquals("1÷", viewModel.mainValueText.get())
        Assertions.assertTrue(viewModel.calculating.get())
    }

    @Test
    fun `tapClear() calculatingがtrueの場合、mainValueTextの末尾の文字列が削除されること`() {
        viewModel.calculating.set(true)
        viewModel.mainValueText.set("123")
        viewModel.tapClear()
        Assertions.assertEquals("12", viewModel.mainValueText.get())
    }

    @Test
    fun `tapClear() calculatingがtrue、かつ、mainValueTextが空の場合、mainValueTextが更新されず、エラーで落ちないこと`() {
        viewModel.calculating.set(true)
        viewModel.mainValueText.set("")
        viewModel.tapClear()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }

    @Test
    fun `tapClear() calculatingがfalseの場合、mainValueTextが空で更新されること`() {
        viewModel.calculating.set(false)
        viewModel.mainValueText.set("123")
        viewModel.tapClear()
        Assertions.assertEquals("", viewModel.mainValueText.get())
    }
}
