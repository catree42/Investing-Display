package com.example.investingdisplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.investingdisplay.databinding.ActivityCurrencyListSettingBinding

class CurrencyListSettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCurrencyListSettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCurrencyListSettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fabList.setOnClickListener(){
            intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}