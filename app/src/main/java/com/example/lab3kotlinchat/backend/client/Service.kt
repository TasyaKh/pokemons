package com.example.lab3kotlinchat.backend.client

import PokemonListResponse
import com.example.lab3kotlinchat.backend.client.entyties.Ability
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("pokemon/{name_id}/")
    fun getPokemon(@Path("name_id") name: String): Call<Pokemon>

    @GET("pokemon")
    fun getPokemonCount(): Call<PokemonListResponse>

    @GET("ability/{id}/")
    fun getAbility(@Path("id") id: Int): Call<Ability>
}