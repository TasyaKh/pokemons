package com.example.lab3kotlinchat.ui.gacha

import android.content.ContentValues
import android.icu.util.Calendar
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class BagViewModel : ViewModel() {
    private val documents = ArrayList<PokemonFirebase>()

    private var _pokemon = MutableLiveData<Pokemon>()
    var pokemon: LiveData<Pokemon> = _pokemon

    private var _pokemonFirebase = MutableLiveData<PokemonFirebase>()
    var pokemonFirebase: LiveData<PokemonFirebase> = _pokemonFirebase

    private val pokeApi = PokemonClient()
    val db = DBConnection()

    var incrementPokemons = 0

    fun getMyPokemons() {


        //получить записи с покемонами
       val query = db.getMyPokemons()
        query.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result) {

//                    TODO: Convertion fanger String to int
                    val pokeId = document.data["id"].toString().toInt()

                    val cal = Calendar.getInstance()
                    cal.timeInMillis =  document.data["date_add"].toString().toLong()

                    documents.add(PokemonFirebase (document.id, pokeId,
                        cal
                    ))

                    println("Calendar.getInstance() "  + Calendar.getInstance().time.time)


//                    val sdf  = SimpleDateFormat( "EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
////                    cal.time=  sdf.parse(document.data["date_add"].toString())
//
//                    println("calculator time " + document.data["date_add"].toString() to Calendar)
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