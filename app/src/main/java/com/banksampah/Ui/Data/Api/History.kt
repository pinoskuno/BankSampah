package com.banksampah.Ui.Data.Api

import com.banksampah.Ui.Data.Respons.UserR

interface HistoryAdapterCallback {
    fun onDelete(modelDatabase: UserR?)
}