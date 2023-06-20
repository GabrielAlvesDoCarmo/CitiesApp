package com.gdsdevtec.citiesapp.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
import com.gdsdevtec.citiesapp.databinding.ActivityMainBinding
import com.gdsdevtec.citiesapp.model.City
import com.gdsdevtec.citiesapp.model.DataStore
import com.gdsdevtec.citiesapp.utils.sample.listCities
import com.gdsdevtec.citiesapp.view.CityAdapter
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var cityAdapter: CityAdapter
    private val activityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                Toast.makeText(
                    this@MainActivity,
                    "Cidade ${intent.getStringExtra("city")} Adicionada com sucesso",
                    Toast.LENGTH_LONG
                ).show()
            }
            cityAdapter.notifyDataSetChanged()
        }
    }
    private val editResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.let { intent ->
                Toast.makeText(
                    this@MainActivity,
                    "Cidade ${intent.getStringExtra("city")} Alterada com sucesso",
                    Toast.LENGTH_LONG
                ).show()
            }
            cityAdapter.notifyDataSetChanged()
        }
    }
    private lateinit var gesture: GestureDetector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActivity()
    }

    private fun setupActivity() {
        gesture = setupGestureDetector()
        cityAdapter = CityAdapter(DataStore.cities).apply {
            binding.rvCities.adapter = this
        }
        binding.fabMain.setOnClickListener { startAddEndEditCity() }
        configureEventsReciclerVIew()
    }

    private fun setupGestureDetector() =
        GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                binding.rvCities.findChildViewUnder(e.x, e.y).run {
                    this?.let { cell ->/**/
                        binding.rvCities.getChildAdapterPosition(cell).apply {
                            Intent(this@MainActivity, RegisterAndEditCityActivity::class.java).run {
                                putExtra("position", this@apply)
                                editResult.launch(this)
                            }
                        }
                    }
                }
                return super.onSingleTapConfirmed(e)
            }

            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)
                binding.rvCities.findChildViewUnder(e.x, e.y).run {
                    this?.let { cell ->/**/
                        binding.rvCities.getChildAdapterPosition(cell).apply {
                            val city = DataStore.getCity(this)
                            AlertDialog.Builder(this@MainActivity).run {
                                setMessage("Deseja excluir essa cidade ?")
                                setPositiveButton("OK") { _, _ ->
                                    DataStore.removeCity(city)
                                    Toast.makeText(
                                        this@MainActivity,
                                        " A Cidade ${city.name} foi excluida com sucesso",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    cityAdapter.notifyDataSetChanged()
                                }
                                setNegativeButton("Nao") { _, _ ->
                                    return@setNegativeButton
                                }
                                show()
                            }
                        }
                    }
                }
            }
        }
        )

    private fun configureEventsReciclerVIew() {
        with(binding.rvCities) {
            addOnItemTouchListener(object : OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    rv.findChildViewUnder(e.x, e.y).run {
                        return (this != null && gesture.onTouchEvent(e))
                    }
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            }
            )
        }
    }

    private fun startAddEndEditCity() {
        Intent(this, RegisterAndEditCityActivity::class.java).run {
            activityResult.launch(this)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        AlertDialog.Builder(this).run {
            setMessage("Deseja sair ?")
            setPositiveButton("OK") { _, _ ->
                this@MainActivity.onBackPressedDispatcher.onBackPressed()
            }
            setNegativeButton("Nao") { _, _ ->
                return@setNegativeButton
            }
            show()
        }
    }
}