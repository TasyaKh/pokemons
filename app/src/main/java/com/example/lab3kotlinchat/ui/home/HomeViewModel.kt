
package com.example.lab3kotlinchat.ui.home

import PokemonListResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab3kotlinchat.backend.client.PokemonClient
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import kotlinx.coroutines.runBlocking

class HomeViewModel : ViewModel() {

    private var _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    val _pokemonCount = MutableLiveData<PokemonListResponse>()
    val pokemonCount: LiveData<PokemonListResponse> = _pokemonCount

    private val pokeApi = PokemonClient()


    fun getPokemons( start:Int = 0, end:Int = 20) {
            for (i in start..end) {
                runBlocking {
                   pokeApi.getPokemon((i+1).toString(),  _pokemon)
                }
            }
//        ps.getPokemonByName("-1", _pokemon)
    }

   fun getPokemonCount() {
       pokeApi.getPokemonList(_pokemonCount)
    }
}