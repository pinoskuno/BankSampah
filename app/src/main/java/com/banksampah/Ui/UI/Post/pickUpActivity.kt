package com.banksampah.Ui.UI.Post

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.banksampah.R
import com.banksampah.Ui.Data.Function.rupiahFormat
import com.banksampah.databinding.ActivityPickUpBinding
import kotlinx.android.synthetic.main.activity_pick_up.*
import java.text.SimpleDateFormat
import java.util.*


class pickUpActivity : AppCompatActivity() {

    lateinit var _pickupVM: ActivityPickUpBinding
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
        setStatusBar()
        setToolbar()
        setInitLayout()
        setInputData()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    private fun setInitLayout() {
        _category = resources.getStringArray(R.array.kategori_sampah)
        _price = resources.getStringArray(R.array.harga_perkilo)

        _pickupVM = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.application)).get(pickUpViewModel::class.java)

        val languageA = ArrayAdapter(this@pickUpActivity, android.R.layout.simple_list_item_1, _category)
        languageA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spKategori.setAdapter(languageA)

        spKategori.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                _trashCategory = parent.getItemAtPosition(position).toString()
                _selectedPrice = _price[position]
                spKategori.setEnabled(true)
                countPrice = _selectedPrice.toInt()
                if (inputBerat.getText().toString() != "") {
                    countWeigh = inputBerat.getText().toString().toInt()
                    setTotalPrice(countWeigh)
                } else {
                    inputHarga.setText(rupiahFormat(countPrice))
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        })

        inputBerat.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(editable: Editable) {
                inputBerat.removeTextChangedListener(this)
                if (editable.length > 0) {
                    countWeigh = editable.toString().toInt()
                    setTotalPrice(countWeigh)
                } else {
                    inputHarga.setText(rupiahFormat(countPrice))
                }
                inputBerat.addTextChangedListener(this)
            }
        })

        inputTanggal.setOnClickListener { view: View? ->
            val pickupDate = Calendar.getInstance()
            val date =
                DatePickerDialog.OnDateSetListener { view1: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    pickupDate[Calendar.YEAR] = year
                    pickupDate[Calendar.MONTH] = monthOfYear
                    pickupDate[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val strFormatDefault = "d MMMM yyyy"
                    val simpleDateFormat = SimpleDateFormat(strFormatDefault, Locale.getDefault())
                    inputTanggal.setText(simpleDateFormat.format(pickupDate.time))
                }
            DatePickerDialog(
                this@pickUpActivity, date,
                pickupDate[Calendar.YEAR],
                pickupDate[Calendar.MONTH],
                pickupDate[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }

    private fun setTotalPrice(weigh: Int) {
        countTotal = countPrice * weigh
        inputHarga.setText(rupiahFormat(countTotal))
    }

    private fun setInputData() {
        btnCheckout.setOnClickListener { v: View? ->
            _name = inputNama.text.toString()
            _date = inputTanggal.text.toString()
            _address = inputAlamat.text.toString()
            _note = inputTambahan.text.toString()
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

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val window = activity.window
            val layoutParams = window.attributes
            if (on) {
                layoutParams.flags = layoutParams.flags or bits
            } else {
                layoutParams.flags = layoutParams.flags and bits.inv()
            }
            window.attributes = layoutParams
        }
    }

}