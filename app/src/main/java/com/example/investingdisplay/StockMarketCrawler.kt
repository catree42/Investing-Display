package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi
import org.jsoup.Jsoup

class StockMarketCrawler : Crawler() {


    //https://finance.naver.com/sise/sise_index.naver?code=KOSPI
    //https://finance.naver.com/sise/sise_index.naver?code=KOSDAQ
    //https://finance.naver.com/sise/sise_index.naver?code=KPI200
    private val baseUrl = "https://finance.naver.com/sise/sise_index.naver?code="
    private val finalUrl = "https://finance.naver.com/"
    override var url: String = baseUrl
    var chartURLs = Array(2) { "" }
    val stocks = arrayOf("KOSPI", "KOSDAQ", "KPI200")
    val charts = arrayOf("day", "day90", "day365", "day1095")

    private fun changeUrl(i:Int){
        changeUrl(baseUrl + stocks[i])
    }

    fun crawlDate(): String {
        val doc = Jsoup.connect(finalUrl).get()
        var str = doc.select("#time").html()
        str = str.replace("</span>", "").replace("<span>", " ")
        return str
    }

    private fun crawlnowPrice():String{
        val str = getElements("#now_value")
        return str.text()
    }

    private fun crawlchangeValue(i:Int):String {
        val query : String
        if (i == 2) {
            query = "#change_value > span"
        }
        else {
            query = "#change_value_and_rate > span"
        }
        val str = getElements(query)
        return str.text().split(" ")[0]
    }

    //결과값 정상적으로 나오는지 확인 필요
    private fun crawlchangeRate(i:Int):String{
        val str : String
        return if(i == 2){
            str = getElements("#change_rate > strong").text()
            str.split("%")[0]
        } else {
            str = getElements("#change_value_and_rate").text()
            str.split(" ")[1].split("%")[0]
        }

    }

    private fun checkIsRised(str: String):Boolean{
        val plusOrMinus = str.substring(0 until 1)

        return plusOrMinus == "+"
    }

    private fun getChartUrls(){
        val str = getElements("#chart_0 > div > img").attr("src")
        chartURLs[0] = str.split("day")[0]
        chartURLs[1] = str.split("day")[1]
    }

    //#chart_0 > div > img
    //https://ssl.pstatic.net/imgstock/chart3/day/KOSPI.png?sidcode=1668182071348
    //#chart_2 > div > img
    //day - day90 - day 365 - day1095


//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun getDate():String{
//        val current = LocalDate.now()
//        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
//        return current.format(formatter)
//    }

    private fun getImgSrc(i: Int): String {
        return chartURLs[0] + charts[i] + chartURLs[1]
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataList():ArrayList<StockMarketData>{
        val stockMarketDataList = ArrayList<StockMarketData>()
        var nowPrice: String
        var changeValue: String
        var changeRate: String
        var isrised: Boolean
        val imgSrcs = Array(charts.size){""}
        for(i in stocks.indices) {
            changeUrl(i)
            getChartUrls()
            nowPrice = crawlnowPrice()
            changeValue = crawlchangeValue(i)
            changeRate = crawlchangeRate(i)
            isrised = checkIsRised(changeRate)

            for(j in charts.indices) {
                imgSrcs[j] = getImgSrc(j)
            }
            stockMarketDataList.add(
                StockMarketData(
                    stocks[i],
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
            )
        }
        return stockMarketDataList
    }
}