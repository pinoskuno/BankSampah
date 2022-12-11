package com.banksampah.Ui.UI.History

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.banksampah.R
import com.banksampah.Ui.Data.Api.History
import com.banksampah.Ui.Data.Function.rupiahFormat
import com.banksampah.Ui.Data.Respons.UserR
import kotlinx.android.synthetic.main.item_history.view.*
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter (
    var _context: Context, _listInput: MutableList<UserR>, adapterCallback: History) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    var _userDB: MutableList<UserR>
    var _historyDB: History

    fun setDataAdapter(items: List<UserR>) {
        _userDB.clear()
        _userDB.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: UserR = _userDB[position]
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        
        holder._tvusername.text = data.username
        holder._tvdate.text = data.date
        holder._tvcategory.text = "Sampah " + data.type
        holder._tvweight.text = "Berat : " + data.weight.toString() + " Kg"
        holder._tvprice.text = "Pendapatan : " + rupiahFormat(data.price)
        if (data.date < currentDate) {
            holder._tvStatus.setTextColor(ContextCompat.getColor(_context, R.color.red))
            holder._tvStatus.text = "Penjemputan Berhasil!"
        } else {
            holder._tvStatus.setTextColor(ContextCompat.getColor(_context, R.color.colorPrimary))
            holder._tvStatus.text = "Masih dalam Proses !"
        }
    }

    override fun getItemCount(): Int {
        return _userDB.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvusername: TextView = itemView.tv_item_name
        var _tvdate: TextView = itemView.tv_item_date
        var _tvcategory: TextView = itemView.tv_item_category
        var _tvweight: TextView = itemView.tv_item_weight
        var _tvprice: TextView = itemView.tv_item_balace
        var _tvStatus: TextView = itemView.tv_item_stat
    }
    
    init {
        _userDB = _listInput
        _historyDB = adapterCallback
    }

}