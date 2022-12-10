package com.banksampah.Ui.Data.Function

import java.text.DecimalFormat

fun rupiahFormat(price: Int): String {
    val formatter = DecimalFormat("#,###")
    return "Rp " + formatter.format(price.toLong())
}