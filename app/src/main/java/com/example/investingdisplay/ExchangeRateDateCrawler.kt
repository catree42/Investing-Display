package com.example.investingdisplay

import org.jsoup.Jsoup

class ExchangeRateDateCrawler : Crawler() {

    override var url: String = "https://finance.naver.com/marketindex/exchangeList.naver"
    private val naverFinanceUrl = "https://finance.naver.com/marketindex/"

    fun crawlDate():String{
        val doc = Jsoup.connect(naverFinanceUrl).get()
        val date = doc.select("#section_ex1 > div > span.date").text()
        return date
    }

    fun crawlStandard():String{
        val doc = Jsoup.connect(naverFinanceUrl).get()
        val standard = doc.select("#section_ex1 > div > span.standard").text()
        return standard
    }
}