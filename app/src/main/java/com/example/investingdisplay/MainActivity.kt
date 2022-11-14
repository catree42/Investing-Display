package com.example.investingdisplay

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TableRow
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
    private var model : ExchangeRateModel? = null
    private lateinit var binding: ActivityMainBinding
    private lateinit var thr:Thread
    private var str:String?=null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvShowChartUSD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(0)?.imgSrcMonth3
        }

        binding.tvShowChartEUR.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(1)?.imgSrcMonth3
        }

        binding.tvShowChartJPY.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(2)?.imgSrcMonth3
        }

        binding.tvShowChartCNY.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(3)?.imgSrcMonth3
        }

        binding.tvShowChartHKD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(4)?.imgSrcMonth3
        }

        binding.tvShowChartTWD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(5)?.imgSrcMonth3
        }

        binding.tvShowChartGBP.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(6)?.imgSrcMonth3
        }

        binding.tvShowChartOMP.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(7)?.imgSrcMonth3
        }

        binding.tvShowChartCAD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(8)?.imgSrcMonth3
        }

        binding.tvShowChartCHF.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=model?.dataList?.get(9)?.imgSrcMonth3
        }

        binding.fabAddCurrency.setOnClickListener() {
            intent = Intent(this, CurrencyListSettingActivity::class.java)
            intent.putExtra("dataList", model?.dataList)
            startActivity(intent)
        }


        val trList = ArrayList<TableRow>()
        trList.add(binding.trUSD)
        trList.add(binding.trEUR)
        trList.add(binding.trJPY)
        trList.add(binding.trCNY)
        trList.add(binding.trHKD)
        trList.add(binding.trTWD)
        trList.add(binding.trGBP)
        trList.add(binding.trOMR)
        trList.add(binding.trCAD)
        trList.add(binding.trCHF)

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


        class WorkThread:Thread(){

            override fun run(){
                //모델 데이터 가져오기
                model = ExchangeRateModel()
                if(intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>? != null){
                    model?.dataList = (intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>?)!!
                }else{
                    model?.setDataList()
                }

                for(i in 0..9){
                    tvList.get(i).text = model?.dataList?.get(i)?.rate
                }

                for(i in 0..9){
                    trList.get(i).visibility = if(model?.dataList?.get(i)?.isChecked!!) View.VISIBLE else View.GONE
                }

                while(true) {
                    //이미지 가져와서 그리기
                    if (str == null)
                        str = model?.dataList?.get(0)?.imgSrcMonth3
                    val imgUrl = URL(str)
                    val conn = imgUrl.openConnection()
                    conn.connect()
                    val nSize = conn.contentLength
                    val bis = BufferedInputStream(conn.getInputStream(), nSize)
                    val imgBitmap = BitmapFactory.decodeStream(bis)
                    bis.close()

                    //이미지 크기 조절
                    val matrix = Matrix()
                    matrix.preScale(1.0f, 1.5f)
                    val temp = Bitmap.createBitmap(
                        imgBitmap,
                        0,
                        0,
                        imgBitmap.width,
                        imgBitmap.height,
                        matrix,
                        false
                    )
                    //val temp = Bitmap.createBitmap(imgBitmap,0,0,100,200)
                    runOnUiThread { binding.ivChart.setImageBitmap(temp) }
                }
            }

        }

        thr=WorkThread()
        thr.start();


//        Thread(Runnable {
//            //모델 데이터 가져오기
//            model = ExchangeRateModel()
//            if(intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>? != null){
//                model?.dataList = (intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>?)!!
//            }else{
//                model?.setDataList()
//            }
//
//            for(i in 0..9){
//                tvList.get(i).text = model?.dataList?.get(i)?.rate
//            }
//
//            for(i in 0..9){
//                trList.get(i).visibility = if(model?.dataList?.get(i)?.isChecked!!) View.VISIBLE else View.GONE
//            }
//
//            //이미지 가져와서 그리기
//            val imgUrl = URL(model?.dataList?.get(0)?.imgSrcMonth3)
//            val conn = imgUrl.openConnection()
//            conn.connect()
//            val nSize = conn.contentLength
//            val bis = BufferedInputStream(conn.getInputStream(),nSize)
//            val imgBitmap = BitmapFactory.decodeStream(bis)
//            bis.close()
//
//            //이미지 크기 조절
//            val matrix = Matrix()
//            matrix.preScale(1.0f, 1.5f)
//            val temp = Bitmap.createBitmap(imgBitmap, 0,0,imgBitmap.width, imgBitmap.height, matrix, false)
//            //val temp = Bitmap.createBitmap(imgBitmap,0,0,100,200)
//            runOnUiThread{binding.ivChart.setImageBitmap(temp)}
//
//        }).start()

    }

}


