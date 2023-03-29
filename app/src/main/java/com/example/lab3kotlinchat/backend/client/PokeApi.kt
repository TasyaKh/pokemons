package com.example.lab3kotlinchat.backend.client

import androidx.lifecycle.MutableLiveData
import com.example.lab3kotlinchat.backend.client.entyties.Ability
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon

interface PokeApi {
    fun getPokemon(name: String,  watcher: MutableLiveData<Pokemon>)
    fun getAbility(id: Int,  watcher: MutableLiveData<Ability>)
}
