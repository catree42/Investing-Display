package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi

class StockMarketModel : Model() {
    var dataList = ArrayList<StockMarketData>()
    override val crawler = StockMarketCrawler()
    override val date = crawler.crawlDate()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setDataList() {
        dataList = StockMarketCrawler().getDataList()
    }
}