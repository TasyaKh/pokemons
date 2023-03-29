package com.example.lab3kotlinchat.ui.gacha

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab3kotlinchat.backend.client.PokemonClient
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.backend.client.entyties.PokemonFirebase
import com.example.lab3kotlinchat.db.DBConnection
import kotlinx.serialization.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.json.JSONObject

class BagViewModel : ViewModel() {
    val documents = ArrayList<Pair<String,String>>()

    private var _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    private var _pokemonFirebase = MutableLiveData<PokemonFirebase>()
    var pokemonFirebase: LiveData<PokemonFirebase> = _pokemonFirebase

    private val pokeApi = PokemonClient()
    val db = DBConnection()

    var incrementPokemons = 0

    fun getMyPokemons() {


       val query = db.getMyPokemons()
        query.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {

                    val pokeId = document.data["id"].toString()
                    documents.add(Pair(document.id, pokeId))

                    Log.d(ContentValues.TAG, document.id + " => date" + document.data["date_add"])
                }

                getPokemon()

            } else {
                Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
            }
        }
    }

fun getPokemon(){
    // Json.decodeFromJsonElement<Pokemon>(JsonObject(document.data))
   // pokeApi.getPokemon(documents[incrementPokemons].second, _pokemon)
    if(incrementPokemons<documents.size){
        //получить покемона
        pokeApi.getPokemonForFirebase(documents[incrementPokemons],_pokemonFirebase)

        incrementPokemons++
    }

}

}