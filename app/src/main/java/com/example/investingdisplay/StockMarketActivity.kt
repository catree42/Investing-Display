package com.example.investingdisplay

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Matrix
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.example.investingdisplay.databinding.ActivityStockMarketBinding
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.io.BufferedInputStream
import java.net.URL


class StockMarketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStockMarketBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedStock  = 0

        binding.smKOSPI.setOnClickListener() {
            selectedStock = 0
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }

        binding.smKOSDAQ.setOnClickListener() {
            selectedStock = 1
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }

        binding.smKOSPI200.setOnClickListener() {
            selectedStock = 2
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        }


        val smNowValueList = ArrayList<TextView>()
        smNowValueList.add(binding.smNowValueKOSPI)
        smNowValueList.add(binding.smNowValueKOSDAQ)
        smNowValueList.add(binding.smNowValueKOSPI200)

        val smChangeValueAndRateList = ArrayList<TextView>()
        smChangeValueAndRateList.add(binding.smChangeValueAndRateKOSPI)
        smChangeValueAndRateList.add(binding.smChangeValueAndRateKOSDAQ)
        smChangeValueAndRateList.add(binding.smChangeValueAndRateKOSPI200)

        Thread(Runnable {
            //모델 데이터 가져오기
            val model = StockMarketModel()
            model.setDataList()

            //텍스트 데이터
            for(i in 0..2){
                smNowValueList[i].text = model.dataList[i].nowValue
                if (model.dataList[i].isrised){
                    smChangeValueAndRateList[i].text = "▴ "+ model.dataList[i].changeValue + " " + model.dataList[i].changeRate+"%"
                    smChangeValueAndRateList[i].setTextColor(Color.RED)
                    smNowValueList[i].setTextColor(Color.RED)
                }
                else{
                    smChangeValueAndRateList[i].text = "▾ "+ model.dataList[i].changeValue + " " + model.dataList[i].changeRate+"%"
                    smChangeValueAndRateList[i].setTextColor(Color.BLUE)
                    smNowValueList[i].setTextColor(Color.BLUE)
                }
            }

            //이미지 가져와서 그리기
            val imgUrl = URL(model.dataList[selectedStock].imgSrcMonth3)
            val conn = imgUrl.openConnection()
            conn.connect()
            val nSize = conn.contentLength
            val bis = BufferedInputStream(conn.getInputStream(),nSize)
            val imgBitmap = BitmapFactory.decodeStream(bis)
            bis.close()

            //이미지 크기 조절
            val matrix = Matrix()
            matrix.preScale(1.0f, 1.0f)
            val temp = Bitmap.createBitmap(imgBitmap, 0,0,imgBitmap.width, imgBitmap.height, matrix, false)
            //val temp = Bitmap.createBitmap(imgBitmap,0,0,100,200)
            runOnUiThread{binding.ivChart.setImageBitmap(temp)}

        }).start()

    }
}