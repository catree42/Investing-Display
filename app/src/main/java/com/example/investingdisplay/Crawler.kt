package com.example.investingdisplay

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

abstract class Crawler {

    abstract val url : String

    private fun getDoc(): Document {
        return Jsoup.connect(url).get()
    }

    fun getElements(query:String): Elements {
        val doc = getDoc()
        return doc.select(query)
    }
}