package com.example.lab3kotlinchat.db

import android.content.ContentValues
import android.icu.util.Calendar
import android.util.Log
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.time.Duration.Companion.milliseconds


class DBConnection() {


    lateinit var db: FirebaseFirestore;

    init {
        db = Firebase.firestore
    }


    //    add pokemon
    //добавить покемона в избарнное
    fun addFavoritePokemon(pokemon: Pokemon) {

        val data: MutableMap<String, Any> = HashMap()
        data["id"] = pokemon.id

        db.collection("favorite_pokemon").document(pokemon.id.toString())[data] = SetOptions.merge()
    }

    //получить покемона из избранного
    fun getFavoritePokemon(id: Int): Task<DocumentSnapshot> {

        val docRef = db.collection("favorite_pokemon").document(id.toString())
        return docRef.get()
    }

    //remove pokemon из избранного
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

    //сохранить моего покемона
    fun saveMyPokemon(pokemon: Pokemon) {

        val data: MutableMap<String, Any> = HashMap()
        data["id"] = pokemon.id
        data["date_add"] = Calendar.getInstance().time.time.milliseconds.inWholeMilliseconds

        db.collection("my_pokemon").document()[data] = SetOptions.merge()
    }

    // получить всех покемонов пользователя
    fun getMyPokemons(): Task<QuerySnapshot> {

        val docRef = db.collection("my_pokemon").get()

        return docRef
    }

    //удалить моего покемона
    fun removeMyPokemon(document: String) {

        val pokemonRef = db.collection("my_pokemon")
        pokemonRef.document(document).delete()
            .addOnCompleteListener {
                if (it.isSuccessful) {

                    Log.d(
                        ContentValues.TAG,
                        " deleted, document: $it"
                    )
                }
            }
    }
}
