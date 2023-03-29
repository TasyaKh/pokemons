package com.example.lab3kotlinchat.ui.messenger

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab3kotlinchat.backend.client.PokemonClient
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon

class MessengerViewModel : ViewModel() {

    private var _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon>  = _pokemon

    val _pExist = MutableLiveData<Boolean>()
    val pExist :LiveData<Boolean> = _pExist

    var messages = ArrayList<Any>()
    private val pokeApi = PokemonClient()


    fun getPokemonByName(name:String){
       pokeApi.getPokemon(name,  _pokemon)
    }

    fun addMessage(msg: Any) {
        messages.add(msg)
    }
}