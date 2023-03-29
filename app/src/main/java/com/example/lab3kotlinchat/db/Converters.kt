package com.example.lab3kotlinchat.db

import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import kotlinx.serialization.*
import kotlinx.serialization.json.*

import org.json.JSONObject

fun pokemonToHashMap(pokemon: Pokemon): HashMap<String, Any> {

    val json = Json.encodeToString(pokemon)

    val jsonObject = JSONObject(json)
    val pHashMap = HashMap<String, Any>()
    for (key in jsonObject.keys()) {
        pHashMap[key] = jsonObject[key]
    }
    println(pHashMap)
    val user = hashMapOf(
        "first" to "Alan",
        "middle" to "Mathison",
        "last" to "Turing",
        "born" to 1912
    )
    return pHashMap
}