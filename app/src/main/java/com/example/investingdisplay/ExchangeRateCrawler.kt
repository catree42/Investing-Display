package com.example.investingdisplay

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ExchangeRateCrawler : Crawler() {


    //https://ssl.pstatic.net/imgfinance/chart/marketindex/area/year10/FX_USDKRW.png?sidcode=20221110
    override var url: String = "https://finance.naver.com/marketindex/exchangeList.naver"
    val chartURL = "https://ssl.pstatic.net/imgfinance/chart/marketindex/area"
    val currencies = arrayOf("USD", "EUR", "JPY", "CNY", "HKD", "TWD", "GBP", "OMR", "CAD", "CHF")


    private fun crawlName(i:Int):String{
        val name = getElements("body > div > table > tbody > tr:nth-child($i) > td.tit > a")
        return name.text()
    }

    private fun crawlRate(i: Int):String{
        val rate = getElements("body > div > table > tbody > tr:nth-child($i) > td.sale")
        return rate.text()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate():String{
        val current = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        return current.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getImgSrc(duration:String, currency:String):String{
        val imgSrc = "$chartURL/$duration/FX_$currency"+"KRW.png?sidcode=${getDate()}"
        return imgSrc
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDataList():ArrayList<ExchangeRateData>{
        val exchangeRateDataList = ArrayList<ExchangeRateData>()
        var name : String
        var rate : String
        var imgSrcMonth : String
        var imgSrcMonth3 : String
        var imgSrcYear : String
        var imgSrcYear3 : String
        var imgSrcYear5 : String
        var imgSrcYear10 : String
        for(i in 1..10){
            name = crawlName(i)
            rate = crawlRate(i)
            imgSrcMonth = getImgSrc("month",currencies.get(i-1))
            imgSrcMonth3 = getImgSrc("month3",currencies.get(i-1))
            imgSrcYear = getImgSrc("year",currencies.get(i-1))
            imgSrcYear3 = getImgSrc("year3",currencies.get(i-1))
            imgSrcYear5 = getImgSrc("year5",currencies.get(i-1))
            imgSrcYear10 = getImgSrc("year10",currencies.get(i-1))
            exchangeRateDataList.add(ExchangeRateData(name,rate,
                imgSrcMonth,imgSrcMonth3,imgSrcYear,imgSrcYear3,imgSrcYear5,imgSrcYear10,
                true))
        }
        return exchangeRateDataList
    }
}