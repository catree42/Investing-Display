package com.example.investingdisplay

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
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
    @RequiresApi(Build.VERSION_CODES.O)
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


        val tvList = ArrayList<TextView>()
        tvList.add(binding.tvPriceUSD)
        tvList.add(binding.tvPriceEUR)
        tvList.add(binding.tvPriceJPY)
        tvList.add(binding.tvPriceCNY)
        tvList.add(binding.tvPriceHKD)
        tvList.add(binding.tvPriceTWD)
        tvList.add(binding.tvPriceGBP)
        tvList.add(binding.tvPriceOMR)
        tvList.add(binding.tvPriceCAD)
        tvList.add(binding.tvPriceCHF)

        Thread(Runnable {
            //모델 데이터 가져오기
            val model = ExchangeRateModel()
            model.setDataList()

            for(i in 0..9){
                tvList.get(i).text = model.dataList.get(i).rate
            }

            for(i in 0..9){
                tvList.get(i).visibility = if(model.dataList.get(i).isChecked) View.VISIBLE else View.GONE
            }

            //이미지 가져와서 그리기
            val imgUrl = URL(model.dataList.get(0).imgSrcMonth3)
            val conn = imgUrl.openConnection()
            conn.connect()
            val nSize = conn.contentLength
            val bis = BufferedInputStream(conn.getInputStream(),nSize)
            val imgBitmap = BitmapFactory.decodeStream(bis)
            bis.close()

            //이미지 크기 조절
            val matrix = Matrix()
            matrix.preScale(1.0f, 1.5f)
            val temp = Bitmap.createBitmap(imgBitmap, 0,0,imgBitmap.width, imgBitmap.height, matrix, false)
            //val temp = Bitmap.createBitmap(imgBitmap,0,0,100,200)
            runOnUiThread{binding.ivChart.setImageBitmap(temp)}

        }).start()

    }

}



