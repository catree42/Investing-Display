package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi

class FacadeExchangeRateCrawler {

    var exchangeRateImageCrawler = ExchangeRateImageCrawler();
    var exchangeRateDateCrawler =ExchangeRateDateCrawler();
    var exchangeRateInfoCrawler=ExchangeRateInfoCrawler();
    var tempDataList = ArrayList<ExchangeRateData>();
    var dataList=ArrayList<ExchangeRateData>();
    var date:String="";
    var standard:String="";

    @RequiresApi(Build.VERSION_CODES.O)
    fun startCrawling(){
        tempDataList=exchangeRateImageCrawler.getDataList();
        dataList=exchangeRateInfoCrawler.getDataList(tempDataList);
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