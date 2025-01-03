package com.example.theanimalsapp.data

import retrofit2.http.GET
import retrofit2.http.Path

data class Animal(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val facts: List<String>
)

data class Environment(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val animals: List<Animal>
)

interface AnimalsApi {
    @GET("animals")
    suspend fun getAnimals(): List<Animal>

    @GET("environments")
    suspend fun getEnvironments(): List<Environment>

    @GET("animals/{id}")
    suspend fun getAnimalDetail(@Path("id") id: Int): Animal

    @GET("environments/{id}")
    suspend fun getEnvironmentDetail(@Path("id") id: Int): Environment
}