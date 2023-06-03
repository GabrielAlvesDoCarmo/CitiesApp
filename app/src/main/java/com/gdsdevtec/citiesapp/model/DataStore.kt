package com.gdsdevtec.citiesapp.model

object DataStore {
    private val cities: MutableList<City> = arrayListOf()
    init {
        cities.add(City("Sao paulo", 200))
        cities.add(City("Rio de janeiro", 300))
        cities.add(City("Recife", 400))
    }
    fun getCity(position: Int) = cities[position]
    fun addCity(city: City) = cities.add(city)
    fun editCity(position: Int, city: City) = cities.set(position, city)
    fun removeCity(city: City) = cities.remove(city)
    fun removeCityForPosition(position: Int) = cities.removeAt(position)
}