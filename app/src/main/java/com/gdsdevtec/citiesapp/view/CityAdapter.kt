package com.gdsdevtec.citiesapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gdsdevtec.citiesapp.databinding.ItemCityRvBinding
import com.gdsdevtec.citiesapp.model.City
@SuppressLint("NotifyDataSetChanged")
class CityAdapter(
    private val cities: MutableList<City>,
) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {
    inner class CityViewHolder(val binding: ItemCityRvBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        return CityViewHolder(
            ItemCityRvBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = cities.size

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        cities[position].apply {
            holder.binding.textCityName.text = this.name
            holder.binding.textQtdPopulation.text = this.people.toString()
        }
    }
}