package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi

class ExchangeRateModel : Model() {
    var dataList = ArrayList<ExchangeRateData>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setDataList() {
        dataList = ExchangeRateCrawler().getDataList()
    }


}