package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi

class FacadeStockMarketCrawler {
    private val stockMarketDataList = ArrayList<StockMarketData>()
    private lateinit var criterionTime : String
    private val foundationUrl = "https://finance.naver.com/"
    private val chartType = arrayOf("day", "day90", "day365", "day1095")

    private val stockMarketDateCrawler = StockMarketDateCrawler(foundationUrl)

    private val kospiStyleCrawlerList = ArrayList<KOSPIStyleCrawler>()
    private val kospi200StyleCrawlerList =  ArrayList<KOSPI200StyleCrawler>()


    private val kospiCrawler = KOSPIStyleCrawler(foundationUrl, "KOSPI", "KOSPI", chartType)
    private val kosdaqCrawler = KOSPIStyleCrawler(foundationUrl, "KOSDAQ", "KOSDAQ", chartType)
    private val kospi200Crawler = KOSPI200StyleCrawler(foundationUrl, "KPI200", "KOSPI200", chartType)

    init {
        kospiStyleCrawlerList.add(kospiCrawler)
        kospiStyleCrawlerList.add(kosdaqCrawler)
        kospi200StyleCrawlerList.add(kospi200Crawler)
    }

    fun getCriterionTime(): String{
        criterionTime = stockMarketDateCrawler.getCriterionTime()

        return criterionTime
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDataList(){
        for(c in kospiStyleCrawlerList){
            stockMarketDataList.add(c.getData())
        }
        for(c in kospi200StyleCrawlerList){
            stockMarketDataList.add(c.getData())
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataList():ArrayList<StockMarketData>{
        setDataList()

        return stockMarketDataList
    }
}