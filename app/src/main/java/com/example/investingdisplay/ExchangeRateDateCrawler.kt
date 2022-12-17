package com.example.investingdisplay

import android.util.Log
import org.jsoup.Jsoup

class ExchangeRateDateCrawler {

    private val naverFinanceUrl = "https://finance.naver.com/marketindex/"

    fun crawlDate():String{
        val doc = Jsoup.connect(naverFinanceUrl).get()
        val date = doc.select("#section_ex1 > div > span.date").text()
        Log.d("date", date)
        return date
    }

    fun crawlStandard():String{
        val doc = Jsoup.connect(naverFinanceUrl).get()
        val standard = doc.select("#section_ex1 > div > span.standard").text()
        Log.d("standard", standard)
        return standard
    }
}