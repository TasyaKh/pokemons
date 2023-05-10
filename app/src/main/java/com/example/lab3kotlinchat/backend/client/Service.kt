package com.example.lab3kotlinchat.backend.client

import PokemonListResponse
import com.example.lab3kotlinchat.backend.client.entyties.Ability
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("pokemon/{name_id}/")
    suspend fun getPokemon(@Path("name_id") name: String): Pokemon

    @GET("pokemon")
    suspend fun getPokemonCount(): PokemonListResponse

    @GET("ability/{id}/")
    suspend fun getAbility(@Path("id") id: Int): Ability
}