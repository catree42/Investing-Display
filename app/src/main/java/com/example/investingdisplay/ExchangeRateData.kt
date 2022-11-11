package com.example.investingdisplay

data class ExchangeRateData(
    val name : String,
    val rate : String,
    val imgSrcMonth : String,
    val imgSrcMonth3 : String,
    val imgSrcYear : String,
    val imgSrcYear3 : String,
    val imgSrcYear5 : String,
    val imgSrcYear10 : String,
    var isChecked:Boolean
)
