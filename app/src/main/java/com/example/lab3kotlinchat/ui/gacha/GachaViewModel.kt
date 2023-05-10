package com.example.lab3kotlinchat.ui.gacha

import PokemonListResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab3kotlinchat.backend.client.PokemonClient
import com.example.lab3kotlinchat.backend.client.entyties.Ability
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.db.DBConnection
import kotlinx.coroutines.launch

class GachaViewModel : ViewModel() {

    //    pokemon
    private var _currentPokemon = MutableLiveData<Pokemon>()
    var currentPokemon: LiveData<Pokemon> = _currentPokemon

    //    ability
    private var _ability = MutableLiveData<Ability>()
    var ability: LiveData<Ability> = _ability

    private val _pokemonCount = MutableLiveData<PokemonListResponse>(PokemonListResponse(0))
    val pokemonCount: LiveData<PokemonListResponse> = _pokemonCount

    private val pokeApi = PokemonClient()

    fun getAbility(id: Int) {
        viewModelScope.launch {
            val ability =   pokeApi.getAbility(id)
            if(ability != null) _ability.value = ability
        }

    }

    fun generateRandomPokemon(from: Int, to: Int) {

        val id = getRandomIdPokemon(from, to)

        viewModelScope.launch {
            val p =  pokeApi.getPokemon(id.toString())
            if(p != null) _currentPokemon.value = p
        }
    }

    fun saveMyPokemon(it: Pokemon) {
        val db = DBConnection()

        db.saveMyPokemon(it)
    }

}