package com.example.investingdisplay

abstract class Model {
    private val dataList = ArrayList<Any>()

    abstract fun setDataList()
}