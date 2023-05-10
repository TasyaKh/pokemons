package com.example.lab3kotlinchat.ui.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab3kotlinchat.backend.client.PokemonClient
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MessengerViewModel : ViewModel() {

    private var _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon>  = _pokemon

    val _pExist = MutableLiveData<Boolean>()
    val pExist :LiveData<Boolean> = _pExist

    var messages = ArrayList<Any>()
    private val pokeApi = PokemonClient()


    fun getPokemonByName(name:String){

        viewModelScope.launch {
            val p =  pokeApi.getPokemon(name)
            if(p != null) _pokemon.value = p
        }
    }

    fun addMessage(msg: Any) {
        messages.add(msg)
    }
}