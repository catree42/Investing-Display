package com.example.investingdisplay

import android.content.Intent
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
import android.view.MenuItem



class StockMarketActivity : AppCompatActivity(), StockMarketOnItemClick{
    private lateinit var binding: ActivityStockMarketBinding
    private lateinit var model : StockMarketModel
    private var imgUrl : String = ""
    private var selectedStock  = 0
    private var selectedPeriod  = 0
    private lateinit var thr:Thread
    lateinit var stockAdapter: StockMarketAdapter
    private var str: String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStockMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)






        thr=WorkThread()
        thr.start()
    }

    private fun setModel() {
        model = StockMarketModel()
        if (intent.getSerializableExtra("dataList") as ArrayList<StockMarketData>? != null) {
            model?.dataList =
                (intent.getSerializableExtra("dataList") as ArrayList<StockMarketData>?)!!
        } else {
            model?.setDataList()
        }
        setDateStandard()
    }

    private fun setImgUrl(stockIndex:Int, periodIndex:Int):String{
        var tempstr = ""
        if (periodIndex == 0)
            tempstr = model.dataList[stockIndex].imgSrcDay
        else if (periodIndex == 1)
            tempstr = model.dataList[stockIndex].imgSrcMonth3
        else if (periodIndex == 2)
            tempstr = model.dataList[stockIndex].imgSrcYear
        else
            tempstr = model.dataList[stockIndex].imgSrcYear3

        return tempstr
    }
    inner class WorkThread:Thread(){
        override fun run(){
            //모델 데이터 가져오기
            setModel()

            runOnUiThread { binding.smTime.text = model.date }
            runOnUiThread { initRecycler_s() }

            while(true) {
                //이미지 가져와서 그리기
                if (imgUrl == "")
                    imgUrl = model.dataList[0].imgSrcMonth3

                val url = URL(imgUrl)
                val conn = url.openConnection()
                conn.connect()
                val nSize = conn.contentLength
                val bis = BufferedInputStream(conn.getInputStream(), nSize)
                val imgBitmap = BitmapFactory.decodeStream(bis)
                bis.close()

                //이미지 크기 조절
                val matrix = Matrix()
                matrix.preScale(1.0f, 1.0f)
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
    fun setDateStandard() {
//        model?.setDate()
//        model?.setStandard()
        runOnUiThread { binding.smTime.text = model?.date }
        runOnUiThread { binding.smStandard.text = model?.standard }
    }

    private fun initRecycler_s() {
        stockAdapter = StockMarketAdapter(this@StockMarketActivity, this)
        binding.rvStockmarket.adapter = stockAdapter

        stockAdapter.datas = model?.dataList!!
        stockAdapter.notifyDataSetChanged()
    }

    override fun onClick_s(data: StockMarketData) {

        imgUrl= data.imgSrcDay

        binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
        binding.sm1day.setOnClickListener() {
            selectedPeriod = 0
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            imgUrl= setImgUrl(selectedStock, selectedPeriod)
        }

        binding.sm3month.setOnClickListener() {
            selectedPeriod = 1
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            imgUrl= setImgUrl(selectedStock, selectedPeriod)
        }

        binding.sm1year.setOnClickListener() {
            selectedPeriod = 2
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            imgUrl= setImgUrl(selectedStock, selectedPeriod)
        }

        binding.sm3year.setOnClickListener() {
            selectedPeriod = 3
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            imgUrl= setImgUrl(selectedStock, selectedPeriod)
        }
    }
}