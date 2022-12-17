package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi

class StockMarketModel : Model() {
    var dataList = ArrayList<StockMarketData>()
    private val crawler = FacadeStockMarketCrawler()
    override val date = crawler.getCriterionTime()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setDataList() {
        dataList = crawler.getDataList()
    }
}