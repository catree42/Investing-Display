package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class KOSPIStyleCrawler(baseUrl : String, targetID : String, nameOfStock : String, chartType : Array<String>){
    private val finalUrl: String
    private val myChartType : Array<String>
    private val myDoc : Document
    private val myName : String
    private val myImgCrawler : StockMarketImageCrawler

    init{
        finalUrl = baseUrl + "sise/sise_index.naver?code=" + targetID
        myChartType = chartType
        myDoc = Jsoup.connect(finalUrl).get()
        myName = nameOfStock
        myImgCrawler = StockMarketImageCrawler(myDoc)
    }

    private fun getElements(query:String): Elements {
        return myDoc.select(query)
    }

    private fun crawlnowPrice():String{
        val str = getElements("#now_value")
        return str.text()
    }

    private fun crawlchangeValue():String {
        val str = getElements("#change_value_and_rate > span")
        return str.text().split(" ")[0]
    }

    private fun crawlchangeRate():String{
        val str = getElements("#change_value_and_rate").text()
        return str.split(" ")[1].split("%")[0]
    }

    private fun checkIsRised(str: String):Boolean{
        val plusOrMinus = str.substring(0 until 1)
        return plusOrMinus == "+"
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getData(): StockMarketData {
        val imgSrcs = Array(myChartType.size) { "" }

        val nowPrice: String = crawlnowPrice()
        val changeValue: String = crawlchangeValue()
        val changeRate: String = crawlchangeRate()
        val isrised = checkIsRised(changeRate)

        for (j in myChartType.indices) {
            imgSrcs[j] = myImgCrawler.getImgSrc(myChartType[j])
        }

        return StockMarketData(
            myName,
            nowPrice,
            changeValue,
            changeRate,
            isrised,
            imgSrcs[0],
            imgSrcs[1],
            imgSrcs[2],
            imgSrcs[3],
            true
        )
    }
}