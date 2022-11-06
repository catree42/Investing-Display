package com.example.investingdisplay

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.investingdisplay.databinding.ActivityMainBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import org.jsoup.Jsoup
import java.io.BufferedInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.net.URLConnection


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvShowChartUSD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }

        binding.fabAddCurrency.setOnClickListener() {
            intent = Intent(this, CurrencyListSettingActivity::class.java)
            startActivity(intent)
        }


//        val url = "https://finance.naver.com/marketindex/exchangeList.naver"
//        val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36"
//        val referer = "https://finance.naver.com/marketindex/?tabSel=exchange"
//        Thread(Runnable {
//            val doc = Jsoup.connect(url)
//                .userAgent(userAgent)
//                .referrer(referer)
//                .get()
//            val currencies = doc.select("td.tit")
//            val sales = doc.select("td.sale")
//            val ele = doc.select("body > div > table > tbody > tr:nth-child(1) > td.sale")
//            binding.tvPriceUSD.text = ele.text()
//        }).start()


        Thread(Runnable {
            val url = "https://finance.naver.com/marketindex/exchangeDetail.naver?marketindexCd=FX_USDKRW"
            val doc = Jsoup.connect(url).get()
            val ele = doc.select("#content > div.spot > div.flash_area > img")
            val imgSrc = ele.attr("src")

            val imgUrl = URL(imgSrc)
            val conn = imgUrl.openConnection()
            conn.connect()

            val nSize = conn.contentLength
            val bis = BufferedInputStream(conn.getInputStream(),nSize)
            val imgBitmap = BitmapFactory.decodeStream(bis)

            bis.close()

            if(imgBitmap == null){
                runOnUiThread{Toast.makeText(this, "null",Toast.LENGTH_SHORT).show()}
            }else{
                runOnUiThread{Toast.makeText(this, "not null",Toast.LENGTH_SHORT).show()}
            }
            runOnUiThread{binding.ivChart.setImageBitmap(imgBitmap)}
        }).start()

    }

}



