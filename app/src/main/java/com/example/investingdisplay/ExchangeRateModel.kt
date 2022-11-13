package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi

class ExchangeRateModel : Model(), java.io.Serializable {
    var dataList = ArrayList<ExchangeRateData>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setDataList() {
        dataList = ExchangeRateCrawler().getDataList()
    }


}