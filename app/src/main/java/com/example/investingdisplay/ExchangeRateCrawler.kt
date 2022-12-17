package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExchangeRateCrawler : Crawler() {

    override var url: String = "https://finance.naver.com/marketindex/exchangeList.naver"
    var exchangeRateImageCrawler = ExchangeRateImageCrawler();
    var exchangeRateDateCrawler =ExchangeRateDateCrawler();
    var dataList = ArrayList<ExchangeRateData>();
    var date:String="";
    var standard:String="";

    @RequiresApi(Build.VERSION_CODES.O)
    public fun startCrawling(){
        dataList=exchangeRateImageCrawler.getDataList();
        date=exchangeRateDateCrawler.crawlDate();
        standard=exchangeRateDateCrawler.crawlStandard();
    }

    fun getCrawledDate():String{
        return date;
    }

    fun getCrawledStandard():String{
        return standard;
    }

    fun getCrawledDatalist():ArrayList<ExchangeRateData>{
        return dataList;
    }
}