package com.example.lab3kotlinchat.db

import android.content.ContentValues
import android.util.Log
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

import kotlin.collections.HashMap


class DBConnection() {


    lateinit var db: FirebaseFirestore;

    init {
        db = Firebase.firestore
    }


//    add pokemon

    fun addFavoritePokemon(pokemon: Pokemon) {

        val favoritePokemon: HashMap<String, Any> = pokemonToHashMap(pokemon)

        val map = Json.encodeToJsonElement(pokemon).jsonObject.toMap()

        val docRef = db.collection("favorite_pokemon").document(pokemon.id.toString())
        docRef.set(mapOf("user" to map))
    }


    fun getFavoritePokemon(id: Int): Task<DocumentSnapshot> {

        val docRef = db.collection("favorite_pokemon").document(id.toString())
        return docRef.get()
    }

    //remove pokemon
    fun removeFavoritePokemon(id: Int) {

        val pokemonRef = db.collection("favorite_pokemon")
        pokemonRef.document(id.toString()).delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    Log.d(
                        ContentValues.TAG,
                        "pokemon by id: ${id} deleted, document: ${it}"
                    )
                }
            }
    }
}
