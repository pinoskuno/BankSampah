package com.banksampah.Ui.Data.Respons

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "userBankSampah")
@Parcelize
data class UserR(
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0,
    @ColumnInfo(name = "nama_pengguna") var username: String,
    @ColumnInfo(name = "jenis_sampah") var type: String,
    @ColumnInfo(name = "berat") var weight: Int = 0,
    @ColumnInfo(name = "harga") var price: Int = 0,
    @ColumnInfo(name = "tanggal") var date: String,
    @ColumnInfo(name = "alamat") var address: String,
    @ColumnInfo(name = "catatan") var notes: String
) : Parcelable