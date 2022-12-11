package com.banksampah.Ui.UI.History

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.banksampah.Ui.Data.Client.ClientDB.Companion.getInstance
import com.banksampah.Ui.Data.Dao.BankDao
import com.banksampah.Ui.Data.Respons.UserR
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers

class HistoryViewModel (application: Application) : AndroidViewModel(application) {
    var _totalBalance: LiveData<Int>
    var dataBank: LiveData<List<UserR>>
    var _bankDao: BankDao?

    fun deleteDataById(uid: Int) {
        Completable.fromAction {
            _bankDao?.deleteSingleData(uid)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
    init {
        _bankDao = getInstance(application)?.appDatabase?.bankDao()
        dataBank = _bankDao!!.getAll()
        _totalBalance = _bankDao!!.getSaldo()
    }

}