package com.example.investingdisplay

import com.example.investingdisplay.CurrencyExchangeRate

class CurrencyExchangeRateList {

    private val list = ArrayList<CurrencyExchangeRate>()

    private fun setList(){
        list.add(CurrencyExchangeRate("USD",1400.0, true))
        list.add(CurrencyExchangeRate("EUR",1400.0, true))
        list.add(CurrencyExchangeRate("JYP",1400.0, true))
        list.add(CurrencyExchangeRate("CNY",1400.0, true))
        list.add(CurrencyExchangeRate("HKD",1400.0, true))
        list.add(CurrencyExchangeRate("TWD",1400.0, true))
        list.add(CurrencyExchangeRate("GBP",1400.0, true))
        list.add(CurrencyExchangeRate("OMR",1400.0, true))
        list.add(CurrencyExchangeRate("CAD",1400.0, true))
        list.add(CurrencyExchangeRate("CHF",1400.0, true))
    }
}