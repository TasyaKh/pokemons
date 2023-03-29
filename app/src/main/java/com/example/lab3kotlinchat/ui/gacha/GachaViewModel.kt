
package com.example.lab3kotlinchat.ui.home

import PokemonListResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab3kotlinchat.backend.client.PokemonClient
import com.example.lab3kotlinchat.backend.client.entyties.Ability
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.db.DBConnection
import com.example.lab3kotlinchat.ui.gacha.getRandomIdPokemon

class GachaViewModel : ViewModel() {

//    pokemon
    private var _currentPokemon = MutableLiveData<Pokemon>()
    var currentPokemon: LiveData<Pokemon> = _currentPokemon

//    ability
    private var _ability = MutableLiveData<Ability>()
    var ability: LiveData<Ability> = _ability

    private val _pokemonCount = MutableLiveData<PokemonListResponse>(PokemonListResponse(0 ))
    val pokemonCount: LiveData<PokemonListResponse> = _pokemonCount

    private val pokeApi = PokemonClient()

    fun getPokemonCount() {
        pokeApi.getPokemonList(_pokemonCount)
    }

    fun getAbility(id:Int) {
        pokeApi.getAbility(id, _ability)
    }

    fun generateRandomPokemon(from:Int, to:Int){

       val id= getRandomIdPokemon(from, to)
       pokeApi.getPokemon(id.toString(), _currentPokemon)
    }

    fun saveMyPokemon(it: Pokemon) {
        val db = DBConnection()

        db.saveMyPokemon(it)
    }

}