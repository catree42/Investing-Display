package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi

class ExchangeRateModel : Model(), java.io.Serializable {
    var dataList = ArrayList<ExchangeRateData>()
    val crawler = ExchangeRateCrawler()
//    override val crawler = ExchangeRateCrawler()
    override val date = crawler.crawlDate()
    val standard = crawler.crawlStandard()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setDataList() {
        dataList = crawler.getDataList()
    }
}