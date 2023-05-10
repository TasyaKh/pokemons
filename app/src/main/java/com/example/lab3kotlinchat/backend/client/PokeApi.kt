package com.example.lab3kotlinchat.backend.client

import com.example.lab3kotlinchat.backend.client.entyties.Ability
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon

interface PokeApi {
    suspend fun getPokemon(name: String):Pokemon?
    suspend fun getAbility(id: Int):Ability?
}
