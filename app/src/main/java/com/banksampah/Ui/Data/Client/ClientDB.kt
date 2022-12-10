package com.banksampah.Ui.Data.Client

import android.content.Context
import androidx.room.Room
import com.banksampah.Ui.Data.DataLocal.LocalDatabase

class ClientDB private constructor(context: Context) {

    var appDatabase: LocalDatabase

    companion object {
        private var mInstance: ClientDB? = null

        fun getInstance(context: Context): ClientDB? {
            if (mInstance == null) {
                mInstance = ClientDB(context)
            }
            return mInstance
        }
    }

    init {
        appDatabase = Room.databaseBuilder(context, LocalDatabase::class.java, "banksampah_db")
            .fallbackToDestructiveMigration()
            .build()
    }
}