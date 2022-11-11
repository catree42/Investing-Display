package com.example.investingdisplay

data class StockMarketData(
    val name : String,
    val nowValue : String,
    val changeValue : String,
    val changeRate : String,
    val isrised : Boolean,
    val imgSrcDay : String,
    val imgSrcMonth3 : String,
    val imgSrcYear : String,
    val imgSrcYear3 : String,
    val isChecked:Boolean
)
