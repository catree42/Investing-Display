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
    private var modelData : ExchangeRateData? = null    //어떤 환율인지 담을 modelData
    private lateinit var binding: ActivityMainBinding
    private lateinit var thr:Thread
    private var str:String?=null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //차트 보기 누르면 해당 환율을 modelData에 저장하고 3개월 차트를 표시하게함
        binding.tvShowChartUSD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(0)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartEUR.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(1)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartJPY.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(2)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartCNY.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(3)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartHKD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(4)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartTWD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(5)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartGBP.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(6)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartOMP.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(7)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartCAD.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(8)
            str=modelData?.imgSrcMonth3
        }

        binding.tvShowChartCHF.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            modelData=model?.dataList?.get(9)
            str=modelData?.imgSrcMonth3
        }

        //선택된 환율에서 누른 개월, 연도의 차트를 표시하게함
        binding.oneMonth.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=modelData?.imgSrcMonth
        }

        binding.threeMonth.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=modelData?.imgSrcMonth3
        }

        binding.oneYear.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=modelData?.imgSrcYear
        }

        binding.threeYear.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=modelData?.imgSrcYear3
        }

        binding.fiveYear.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=modelData?.imgSrcYear5
        }

        binding.tenYear.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str=modelData?.imgSrcYear10
        }

        //통화 선택 화면으로 바꾸게함
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
                setDateStandard()
                if(intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>? != null){
                    model?.dataList = (intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>?)!!
                }else{
                    model?.setDataList()
                }

                for(i in 0..9){
                    tvList.get(i).text = model?.dataList?.get(i)?.rate
                }

                runOnUiThread {
                    for(i in 0..9){
                        trList.get(i).visibility = if(model?.dataList?.get(i)?.isChecked!!) View.VISIBLE else View.GONE
                    }
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
    }

    fun setDateStandard(){
//        model?.setDate()
//        model?.setStandard()
        runOnUiThread { binding.tvDate.text = model?.date }
        runOnUiThread{binding.tvStandard.text = model?.standard}
    }
}


