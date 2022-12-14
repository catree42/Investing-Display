package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi

class ExchangeRateModel : Model(), java.io.Serializable {
    var dataList = ArrayList<ExchangeRateData>()
    override val crawler = ExchangeRateCrawler()
    override var date ="";
    var standard = "";

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setDataList() {
        crawler.startCrawling();
        date=crawler.getCrawledDate();
        standard=crawler.getCrawledStandard();
        dataList=crawler.getCrawledDatalist();
    }
}