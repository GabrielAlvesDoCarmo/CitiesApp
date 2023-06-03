package com.gdsdevtec.citiesapp.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gdsdevtec.citiesapp.databinding.ActivityMainBinding
import com.gdsdevtec.citiesapp.model.City
import com.gdsdevtec.citiesapp.utils.sample.listCities
import com.gdsdevtec.citiesapp.view.CityAdapter

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var cityAdapter: CityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActivity()
    }

    private fun setupActivity() {
        cityAdapter = CityAdapter(listCities)
        binding.rvCities.apply { adapter = cityAdapter }
    }
}