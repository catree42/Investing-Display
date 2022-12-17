package com.example.investingdisplay

import org.jsoup.nodes.Document

class StockMarketImageCrawler(doc :Document) {
    private var chartURLs = Array(2) { "" }
    private val myDoc : Document
    init {
        myDoc = doc
        getChartUrl()
    }

    private fun getChartUrl(){
        val str = myDoc.select("#chart_0 > div > img").attr("src")
        chartURLs[0] = str.split("day")[0]
        chartURLs[1] = str.split("day")[1]
    }

    fun getImgSrc(chartType: String): String {
        return chartURLs[0] + chartType + chartURLs[1]
    }
}