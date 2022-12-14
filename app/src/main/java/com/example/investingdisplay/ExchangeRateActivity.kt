package com.example.investingdisplay

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.investingdisplay.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.io.BufferedInputStream
import java.net.URL

class ExchangeRateActivity : AppCompatActivity(), ExchangeRateOnItemClick {
    private var model: ExchangeRateModel? = null
    private var modelData: ExchangeRateData? = null    //어떤 환율인지 담을 modelData
    private lateinit var binding: ActivityMainBinding
    private lateinit var thr: Thread
    private var str: String? = null

    lateinit var exchangeRateAdapter: ExchangeRateAdapter

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bnv_main = findViewById<BottomNavigationView>(R.id.bnv)

        bnv_main.setOnItemSelectedListener { item ->

                when (item.itemId) {
                    R.id.nav_exchangeRate -> {

                    }
                    R.id.nav_stock -> {
                        intent = Intent(this, StockMarketActivity::class.java)

                        startActivity(intent)
                    }

                }

            true
        }
        bnv_main.selectedItemId = R.id.nav_exchangeRate

        //통화 선택 화면으로 바꾸게함
        binding.fabAddCurrency.setOnClickListener() {
            intent = Intent(this, CurrencyListSettingActivity::class.java)
            intent.putExtra("dataList", model?.dataList)
            startActivity(intent)
        }
        model = ExchangeRateModel()
        thr = WorkThread()
        thr.start();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setModel() {
        if (intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>? != null) {
            model?.setDataList()
            model?.dataList =
                (intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>?)!!
        } else {
            model?.setDataList()
        }
        setDateStandard()
    }

    private fun drawChart() {
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
        runOnUiThread { binding.ivChart.setImageBitmap(temp) }
    }

    inner class WorkThread : Thread() {

        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            //모델 데이터 가져오기
            setModel()
            runOnUiThread { initRecycler() }


            while (true) {
                //이미지 가져와서 그리기
                drawChart()
            }
        }
    }

    fun setDateStandard() {
        runOnUiThread { binding.tvDate.text = model?.date }
        runOnUiThread { binding.tvStandard.text = model?.standard }
    }

    private fun initRecycler() {
        exchangeRateAdapter = ExchangeRateAdapter(this@ExchangeRateActivity, this)
        binding.rvExchangeRate.adapter = exchangeRateAdapter

        exchangeRateAdapter.datas = model?.dataList!!
        exchangeRateAdapter.notifyDataSetChanged()
    }

    override fun onClick(data: ExchangeRateData) {

        str = data.imgSrcMonth3

        binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED

        binding.oneMonth.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str = data?.imgSrcMonth

        }

        binding.threeMonth.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str = data?.imgSrcMonth3
        }

        binding.oneYear.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str = data?.imgSrcYear
        }

        binding.threeYear.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str = data?.imgSrcYear3
        }

        binding.fiveYear.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str = data?.imgSrcYear5
        }

        binding.tenYear.setOnClickListener() {
            binding.slidePanel.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
            str = data?.imgSrcYear10
        }
    }
}

