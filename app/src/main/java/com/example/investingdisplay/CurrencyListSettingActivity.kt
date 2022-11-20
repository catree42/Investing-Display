package com.example.investingdisplay

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.investingdisplay.databinding.ActivityCurrencyListSettingBinding

class CurrencyListSettingActivity : AppCompatActivity(){
    private lateinit var model : ExchangeRateModel
    private lateinit var binding: ActivityCurrencyListSettingBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCurrencyListSettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val cbList = ArrayList<CheckBox>()
        cbList.add(binding.cbUSD)
        cbList.add(binding.cbEUR)
        cbList.add(binding.cbJPY)
        cbList.add(binding.cbCNY)
        cbList.add(binding.cbHKD)
        cbList.add(binding.cbTWD)
        cbList.add(binding.cbGBP)
        cbList.add(binding.cbOMR)
        cbList.add(binding.cbCAD)
        cbList.add(binding.cbCHF)

        Thread(Runnable {
            //runOnUiThread{Toast.makeText(this,"test",Toast.LENGTH_LONG).show()}
            model = ExchangeRateModel()
            model.dataList = intent.getSerializableExtra("dataList") as ArrayList<ExchangeRateData>

            runOnUiThread {
                for(i in 0..9){
                    cbList.get(i).isChecked = model.dataList.get(i).isChecked
                }
            }

            binding.cbUSD.setOnCheckedChangeListener(CheckboxListener())
            binding.cbEUR.setOnCheckedChangeListener(CheckboxListener())
            binding.cbJPY.setOnCheckedChangeListener(CheckboxListener())
            binding.cbCNY.setOnCheckedChangeListener(CheckboxListener())
            binding.cbHKD.setOnCheckedChangeListener(CheckboxListener())
            binding.cbTWD.setOnCheckedChangeListener(CheckboxListener())
            binding.cbGBP.setOnCheckedChangeListener(CheckboxListener())
            binding.cbOMR.setOnCheckedChangeListener(CheckboxListener())
            binding.cbCAD.setOnCheckedChangeListener(CheckboxListener())
            binding.cbCHF.setOnCheckedChangeListener(CheckboxListener())

        }).start()


        binding.fabList.setOnClickListener(){
            intent = Intent(this,MainActivity::class.java)
            intent.putExtra("dataList", model.dataList)
            startActivity(intent)
        }
    }

    inner class CheckboxListener:CompoundButton.OnCheckedChangeListener{
        override fun onCheckedChanged(view: CompoundButton?, isChecked: Boolean) {
            when(view?.id){
                R.id.cb_USD ->
                    if(isChecked) model.dataList.get(0).isChecked = true
                    else model.dataList.get(0).isChecked = false
                R.id.cb_EUR ->
                    if(isChecked) model.dataList.get(1).isChecked = true
                    else model.dataList.get(1).isChecked = false
                R.id.cb_JPY ->
                    if(isChecked) model.dataList.get(2).isChecked = true
                    else model.dataList.get(2).isChecked = false
                R.id.cb_CNY ->
                    if(isChecked) model.dataList.get(3).isChecked = true
                    else model.dataList.get(3).isChecked = false
                R.id.cb_HKD ->
                    if(isChecked) model.dataList.get(4).isChecked = true
                    else model.dataList.get(4).isChecked = false
                R.id.cb_TWD ->
                    if(isChecked) model.dataList.get(5).isChecked = true
                    else model.dataList.get(5).isChecked = false
                R.id.cb_GBP ->
                    if(isChecked) model.dataList.get(6).isChecked = true
                    else model.dataList.get(6).isChecked = false
                R.id.cb_OMR ->
                    if(isChecked) model.dataList.get(7).isChecked = true
                    else model.dataList.get(7).isChecked = false
                R.id.cb_CAD ->
                    if(isChecked) model.dataList.get(8).isChecked = true
                    else model.dataList.get(8).isChecked = false
                R.id.cb_CHF ->
                    if(isChecked) model.dataList.get(9).isChecked = true
                    else model.dataList.get(9).isChecked = false
            }
        }

    }
}