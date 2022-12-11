package com.banksampah.Ui.UI.Post

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.banksampah.Ui.Data.Client.ClientDB.Companion.getInstance
import com.banksampah.Ui.Data.Dao.BankDao
import com.banksampah.Ui.Data.Respons.UserR
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class pickUpViewModel (application: Application) : AndroidViewModel(application) {

    var databaseDao: BankDao?

    fun addDataSampah(
        nama_pengguna: String,
        jenis_sampah: String,
        berat: Int,
        harga: Int,
        tanggal: String,
        alamat: String,
        catatan: String
    ) {
        Completable.fromAction {
            val _userR = UserR(
                    namaPengguna = nama_pengguna,
                    jenisSampah = jenis_sampah,
                    berat = berat,
                    harga = harga,
                    tanggal = tanggal,
                    alamat = alamat,
                    catatan = catatan)
            databaseDao?.insertData(_userR)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    init {
        databaseDao = getInstance(application)?.appDatabase?.databaseDao()
    }

}