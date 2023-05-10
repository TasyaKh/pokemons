package com.example.lab3kotlinchat.ui.home

import PokemonListResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab3kotlinchat.backend.client.PokemonClient
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.backend.client.entyties.PokemonFirebase
import kotlinx.coroutines.*

class HomeViewModel : ViewModel() {

    private var _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    val _pokemonCount = MutableLiveData<PokemonListResponse>()
    val pokemonCount: LiveData<PokemonListResponse> = _pokemonCount

    val pokeList = MutableLiveData<List<Pokemon>>()

    private val pokeApi = PokemonClient()


    var pokemons = ArrayList<Pokemon>()
    fun getPokemons(start: Int = 0, end: Int = 20) {
        for (i in start..end) {

            viewModelScope.launch {
                val p =  pokeApi.getPokemon((i+1).toString())
                if(p != null) {
                    _pokemon.value = p

                }
            }

        }
    }
//        ps.getPokemonByName("-1", _pokemon)

}