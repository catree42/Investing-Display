package com.example.investingdisplay

abstract class Model {
    abstract val crawler:Crawler
    abstract val date : String
    abstract fun setDataList()
}