package com.gdsdevtec.citiesapp.controller

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.gdsdevtec.citiesapp.databinding.ActivityRegisterAndEditCityBinding
import com.gdsdevtec.citiesapp.model.City
import com.gdsdevtec.citiesapp.model.DataStore

class RegisterAndEditCityActivity : AppCompatActivity() {
    private val binding: ActivityRegisterAndEditCityBinding by lazy {
        ActivityRegisterAndEditCityBinding.inflate(
            layoutInflater
        )
    }
    private var position = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getPosition()
        setupListeners()
    }

    private fun getPosition() {
        intent.getIntExtra("position", -1).apply {
            position = this
            if (position != -1) setEditCity(position)
        }
    }

    private fun setupListeners() = binding?.run {
        btnSave.setOnClickListener {
            saveCity()
        }
        btnCancel.setOnClickListener {
            clickCancel()
        }
    }

    private fun clickCancel() {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun saveCity() {
        generateCity()?.let { safeCity ->
            if (position != -1) DataStore.editCity(position, safeCity) else DataStore.addCity(
                safeCity
            )
            setResult(RESULT_OK)
            finish()
        } ?: showMessage()
    }

    private fun setEditCity(position: Int) = DataStore.getCity(position).run {
        binding.txtName.setText(this.name)
        binding.txtPeople.setText(this.people.toString())
    }

    private fun generateCity(): City? = binding.run {
        val name = txtName.text.toString()
        val peoples = txtPeople.text.toString().toIntOrNull() ?: 0
        if (name.isEmpty() && peoples == 0) return@run null
        return@run City(name, peoples)
    }


    private fun showMessage(msg: String = "Campos invalidos") {
        AlertDialog.Builder(this).run {
            title = "Cities App"
            setMessage(msg)
            setPositiveButton("OK", null)
            show()
        }
    }
}