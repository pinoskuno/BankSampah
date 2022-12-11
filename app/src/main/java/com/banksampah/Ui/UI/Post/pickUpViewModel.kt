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

    var _bankDao: BankDao?

    fun addDataSampah(
        _username: String,
        _type: String,
        _weight: Int,
        _price: Int,
        _date: String,
        _address: String,
        _notes: String
    ) {
        Completable.fromAction {
            val _userR = UserR(
                    username = _username,
                    type = _type,
                    weight = _weight,
                    price = _price,
                    date = _date,
                    address = _address,
                    notes = _notes)
            _bankDao?.insertData(_userR)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    init {
        _bankDao = getInstance(application)?.appDatabase?.bankDao()
    }

}