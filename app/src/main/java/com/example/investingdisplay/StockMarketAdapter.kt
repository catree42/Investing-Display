package com.example.investingdisplay

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StockMarketAdapter(private val context: Context, listener: StockMarketOnItemClick)
    :RecyclerView.Adapter<StockMarketAdapter.ViewHolder>(){
    var datas = ArrayList<StockMarketData>()

    private val mCallback = listener

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val tvName_s : TextView = itemView.findViewById(R.id.tv_name_s)
        private val tvNow_s : TextView = itemView.findViewById(R.id.tv_now_s)
        private val tvUpDown_s : TextView = itemView.findViewById(R.id.tv_updown_s)
        private val tlstock : TableLayout = itemView.findViewById(R.id.tl_stockmarket)

        fun bind(item:StockMarketData){

            if(item.isChecked){
                tlstock.visibility = View.VISIBLE
            }else{

                val params = tlstock.layoutParams
                params.height = 0
                tlstock.layoutParams = params
            }
            tvName_s.text = item.name
            tvNow_s.text = item.nowValue
            tvUpDown_s.text = item.nowValue +" / "+item.changeRate
            if (item.isrised){
                tvUpDown_s.text = "▴ "+ item.nowValue + "/ " + item.changeRate+"%"
                tvUpDown_s.setTextColor(Color.RED)
                tvNow_s.setTextColor(Color.RED)

            }
            else{
                tvUpDown_s.text = "▾ "+ item.nowValue + "/ " + item.changeRate+"%"
                tvUpDown_s.setTextColor(Color.BLUE)
                tvNow_s.setTextColor(Color.BLUE)
            }



            tvUpDown_s.setOnClickListener {
                mCallback.onClick_s(item)

            }
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.stockmarket_item_recycler ,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}