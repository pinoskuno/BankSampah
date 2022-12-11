package com.banksampah.Ui.UI.Post

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.banksampah.R
import com.banksampah.Ui.Data.Function.rupiahFormat
import kotlinx.android.synthetic.main.activity_pick_up.*
import java.text.SimpleDateFormat
import java.util.*


class pickUpActivity : AppCompatActivity() {

    lateinit var _pickupVM: pickUpViewModel
    lateinit var _name: String
    lateinit var _date: String
    lateinit var _address: String
    lateinit var _note: String
    lateinit var _trashCategory: String
    lateinit var _selectedPrice: String
    lateinit var _category: Array<String>
    lateinit var _price: Array<String>
    var countTotal = 0
    var countWeigh = 0
    var countPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_up)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        _category = resources.getStringArray(R.array.kategori_sampah)
        _price = resources.getStringArray(R.array.harga_perkilo)
        _pickupVM = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(pickUpViewModel::class.java)
        val languageA = ArrayAdapter(this@pickUpActivity, android.R.layout.simple_list_item_1, _category)
        languageA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spKategori.adapter = languageA

        spKategori.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                _trashCategory = parent.getItemAtPosition(position).toString()
                _selectedPrice = _price[position]
                spKategori.isEnabled = true
                countPrice = _selectedPrice.toInt()
                if (ed_pickup_weight.text.toString() != "") {
                    countWeigh = ed_pickup_weight.text.toString().toInt()
                    setTotalPrice(countWeigh)
                } else {
                    ed_picup_price.setText(rupiahFormat(countPrice))
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        ed_pickup_weight.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(editable: Editable) {
                ed_pickup_weight.removeTextChangedListener(this)
                if (editable.length > 0) {
                    countWeigh = editable.toString().toInt()
                    setTotalPrice(countWeigh)
                } else {
                    ed_picup_price.setText(rupiahFormat(countPrice))
                }
                ed_pickup_weight.addTextChangedListener(this)
            }
        })

        ed_pickup_dade.setOnClickListener { view: View? ->
            val pickupDate = Calendar.getInstance()
            val date =
                DatePickerDialog.OnDateSetListener { view1: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    pickupDate[Calendar.YEAR] = year
                    pickupDate[Calendar.MONTH] = monthOfYear
                    pickupDate[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val strFormatDefault = "d MMMM yyyy"
                    val simpleDateFormat = SimpleDateFormat(strFormatDefault, Locale.getDefault())
                    ed_pickup_dade.setText(simpleDateFormat.format(pickupDate.time))
                }
            DatePickerDialog(
                this@pickUpActivity, date,
                pickupDate[Calendar.YEAR],
                pickupDate[Calendar.MONTH],
                pickupDate[Calendar.DAY_OF_MONTH]
            ).show()
        }

        btnCheckout.setOnClickListener { v: View? ->
            _name = ed_pickup_name.text.toString()
            _date = ed_pickup_dade.text.toString()
            _address = ed_pickpu_address.text.toString()
            _note = ed_pickup_detail.text.toString()
            if (_name.isEmpty() or _date.isEmpty() or _address.isEmpty() or (_category.size == 0) or (countWeigh == 0) or (countPrice == 0)) {
                Toast.makeText(
                    this@pickUpActivity,
                    "Data tidak boleh ada yang kosong!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                _pickupVM.addDataSampah(
                    _name,
                    _trashCategory,
                    countWeigh,
                    countPrice,
                    _date,
                    _address,
                    _note
                )
                Toast.makeText(
                    this@pickUpActivity,
                    "Pesanan Anda sedang diproses, cek di menu riwayat",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }


    private fun setTotalPrice(weigh: Int) {
        countTotal = countPrice * weigh
        ed_picup_price.setText(rupiahFormat(countTotal))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}