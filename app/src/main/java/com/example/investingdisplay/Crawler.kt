package com.example.investingdisplay

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

abstract class Crawler {

    abstract val url : String

    private fun getDoc(): Document {
        return Jsoup.connect(url).get()
    }

    fun getElements(cssQuery:String): Elements {
        val eles = getDoc().select(cssQuery)
        return eles
    }
}