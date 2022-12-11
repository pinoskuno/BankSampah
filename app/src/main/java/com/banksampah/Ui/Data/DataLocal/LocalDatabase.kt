package com.banksampah.Ui.Data.DataLocal

import androidx.room.Database
import androidx.room.RoomDatabase
import com.banksampah.Ui.Data.Dao.BankDao
import com.banksampah.Ui.Data.Respons.UserR


@Database(entities = [UserR::class], version = 1, exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun bankDao(): BankDao?
}