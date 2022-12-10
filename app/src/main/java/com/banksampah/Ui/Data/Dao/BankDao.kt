package com.banksampah.Ui.Data.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.banksampah.Ui.Data.Respons.UserR

@Dao
interface BankDao {
    @Query("SELECT * FROM userBankSampah")
    fun getAll(): LiveData<List<UserR>>

    @Query("SELECT SUM(harga) FROM userBankSampah")
    fun getSaldo(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(modelDatabases: UserR)

    @Query("DELETE FROM userBankSampah WHERE uid= :uid")
    fun deleteSingleData(uid: Int)
}