package com.example.investingdisplay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExchangeRateAdapter(private val context: Context, listener:ExchangeRateOnItemClick)
    :RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder>(){
    var datas = ArrayList<ExchangeRateData>()
    private val mCallback = listener

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
            private val tvName : TextView = itemView.findViewById(R.id.tv_name)
            private val tvRate : TextView = itemView.findViewById(R.id.tv_rate)
            private val tvChart : TextView = itemView.findViewById(R.id.tv_chart)
            private val tlExchangeRate : TableLayout = itemView.findViewById(R.id.tl_exchangeRate)

            fun bind(item:ExchangeRateData){
                if(item.isChecked){
                    tlExchangeRate.visibility = View.VISIBLE
                }else{
                    //tlExchangeRate.visibility = View.GONE
                    val params = tlExchangeRate.layoutParams
                    params.height = 0
                    tlExchangeRate.layoutParams = params
                }
                tvName.text = item.name
                tvRate.text = item.rate
                tvChart.setOnClickListener {
                    mCallback.onClick(item)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.exchange_rate_item_recycler ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}