package com.example.investingdisplay

import org.jsoup.Jsoup

class StockMarketDateCrawler(timeLocUrl: String) {
    private lateinit var criterionTime : String
    private val myTimeLocUrl : String

    init{
        this.myTimeLocUrl = timeLocUrl
    }

    private fun crawlCriterionTime(){
        val doc = Jsoup.connect(this.myTimeLocUrl).get()
        var str = doc.select("#time").html()
        str = str.replace("</span>", "").replace("<span>", " ")

        this.criterionTime = str
    }

    fun getCriterionTime(): String{
        crawlCriterionTime()

        return criterionTime
    }



}