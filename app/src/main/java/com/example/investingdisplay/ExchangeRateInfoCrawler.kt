package com.example.investingdisplay

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

class ExchangeRateInfoCrawler {
    var url: String = "https://finance.naver.com/marketindex/exchangeList.naver"

    private fun getDoc(): Document {
        return Jsoup.connect(url).get()
    }

    fun getElements(query:String): Elements {
        val doc = getDoc()
        return doc.select(query)
    }

    private fun crawlName(i:Int):String{
        val name = getElements("body > div > table > tbody > tr:nth-child($i) > td.tit > a")
        return name.text()
    }

    private fun crawlRate(i: Int):String{
        val rate = getElements("body > div > table > tbody > tr:nth-child($i) > td.sale")
        return rate.text()
    }

    fun getDataList(list: ArrayList<ExchangeRateData>):ArrayList<ExchangeRateData>{
        val exchangeRateDataList = ArrayList<ExchangeRateData>()
        var data:ExchangeRateData
        for(i in 0..9) {
            data= list[i]
            data.name = crawlName(i+1)
            data.rate = crawlRate(i+1)
            exchangeRateDataList.add(data)
        }
        return exchangeRateDataList
    }
}