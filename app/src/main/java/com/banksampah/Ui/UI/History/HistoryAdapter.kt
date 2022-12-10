package com.banksampah.Ui.UI.History

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.banksampah.R
import com.banksampah.Ui.Data.Api.HistoryAdapterCallback
import com.banksampah.Ui.Data.DataLocal.LocalDatabase
import com.banksampah.Ui.Data.Function.rupiahFormat
import com.banksampah.Ui.Data.Respons.UserR
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryAdapter (
    var mContext: Context,
    modelInputList: MutableList<UserR>,
    adapterCallback: HistoryAdapterCallback
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var modelDatabase: MutableList<UserR>
    var mAdapterCallback: HistoryAdapterCallback

    fun setDataAdapter(items: List<UserR>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data: UserR = modelDatabase[position]

        holder.tvNama.setText(data.namaPengguna)
        holder.tvDate.setText(data.tanggal)
        holder.tvKategori.text = "Sampah " + data.jenisSampah
        holder.tvBerat.text = "Berat : " + data.berat.toString() + " Kg"
        holder.tvSaldo.text = "Pendapatan : " + rupiahFormat(data.harga)

        if (data.berat < 5) {
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red))
            holder.tvStatus.text = "Masih dalam proses"
        } else {
            holder.tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
            holder.tvStatus.text = "Sudah di konfirmasi"
        }
    }

    override fun getItemCount(): Int {
        return modelDatabase.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvNama: TextView = itemView.tvNama
        var tvDate: TextView = itemView.tvDate
        var tvKategori: TextView = itemView.tvKategori
        var tvBerat: TextView = itemView.tvBerat
        var tvSaldo: TextView = itemView.tvSaldo
        var tvStatus: TextView = itemView.tvStatus
        var imageDelete: ImageView = itemView.imageDelete

        init {
            imageDelete.setOnClickListener { view: View? ->
                val modelLaundry: UserR = modelDatabase[adapterPosition]
                mAdapterCallback.onDelete(modelLaundry)
            }
        }
    }



    init {
        modelDatabase = modelInputList
        mAdapterCallback = adapterCallback
    }

}