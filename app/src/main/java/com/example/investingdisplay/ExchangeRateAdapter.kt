package com.example.investingdisplay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class ExchangeRateAdapter(private val context: Context, listener:OnItemClick)
    :RecyclerView.Adapter<ExchangeRateAdapter.ViewHolder>(){
    var datas = ArrayList<ExchangeRateData>()
    //private val v  = LayoutInflater.from(context)
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
                    //Toast.makeText(context, "text",Toast.LENGTH_SHORT).show()
                    //val slidePanel : SlidingUpPanelLayout = v.findViewById(R.id.slidePanel)
                    //slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
                    //LayoutInflater(context).inflate(R.layout.activity_main)
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