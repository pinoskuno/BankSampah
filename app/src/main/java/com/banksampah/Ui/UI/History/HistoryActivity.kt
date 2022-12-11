package com.banksampah.Ui.UI.History

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.banksampah.R
import com.banksampah.Ui.Data.Api.History
import com.banksampah.Ui.Data.Function.rupiahFormat
import com.banksampah.Ui.Data.Respons.UserR
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity(), History {

    var modelDatabaseList: MutableList<UserR> = ArrayList()
    lateinit var _historyA: HistoryAdapter
    lateinit var _HistoryVM: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        tvNotFound.visibility = View.GONE
        _historyA = HistoryAdapter(this, modelDatabaseList, this)
        rvHistory.setHasFixedSize(true)
        rvHistory.layoutManager = LinearLayoutManager(this)
        rvHistory.adapter = _historyA

        _HistoryVM = ViewModelProviders.of(this).get(HistoryViewModel::class.java)


        _HistoryVM._totalBalance.observe(this) { integer ->
            if (integer == null) {
                val _totalBalance = 0
                val balance = rupiahFormat(_totalBalance)
                tv_history_balace.text = balance
            } else {
                val balance = rupiahFormat(integer)
                tv_history_balace.text = balance
            }
        }

        _HistoryVM.dataBank.observe(this) { modelDatabases: List<UserR> ->
            if (modelDatabases.isEmpty()) {
                tvNotFound.visibility = View.VISIBLE
                rvHistory.visibility = View.GONE
            } else {
                tvNotFound.visibility = View.GONE
                rvHistory.visibility = View.VISIBLE
            }
            _historyA.setDataAdapter(modelDatabases)
        }

    }

    override fun onDelete(modelDatabase: UserR?) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


}