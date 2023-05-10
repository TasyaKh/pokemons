package com.example.lab3kotlinchat.ui.gacha

import android.content.ContentValues
import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab3kotlinchat.backend.client.PokemonClient
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.backend.client.entyties.PokemonFirebase
import com.example.lab3kotlinchat.db.DBConnection
import com.example.lab3kotlinchat.ui.BagMyPokemonsAdapter
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import java.util.*
import kotlin.collections.ArrayList

class BagViewModel : ViewModel() {

    val adapter = BagMyPokemonsAdapter()

    private val documents = ArrayList<PokemonFirebase>()

    private var _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    private var _pokemonFirebase = MutableLiveData<PokemonFirebase>()
    var pokemonFirebase: LiveData<PokemonFirebase> = _pokemonFirebase

    private val pokeApi = PokemonClient()
    private val db = DBConnection()

    private var incrementPokemons = 0

    fun getMyPokemons() {

        //получить записи с покемонами
        val query = db.getMyPokemons()
        query.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {

                    val pokeId = document.data["id"].toString().toInt()

                    val cal = Calendar.getInstance()
                    cal.timeInMillis = document.data["date_add"].toString().toLong()

                    documents.add(
                        PokemonFirebase(
                            document.id, pokeId,
                            cal
                        )
                    )

                }

                getPokemons()

            } else {
                Log.d(ContentValues.TAG, "Error getting documents: ", task.exception)
            }
        }
    }


    //    получить покемонов с сервера
    private fun getPokemons() {

        //получить покемона
//        adapter.clearAll()
        for (doc in documents) {

            viewModelScope.launch {
//                ожидаем получения покемона с сервера
//                await here
                val pkm = withContext(Dispatchers.Default) {
                    pokeApi.getPokemon(
                        doc
                            .pokeId
                            .toString()
                    )
                }

                println("helllo ")
                val pF = PokemonFirebase(doc.document, doc.pokeId, doc.dateAdd)
                pF.pokemon = pkm

                adapter.addPokemon(pF)
                //_pokemonFirebase.value = pF
            }
        }

    }

}