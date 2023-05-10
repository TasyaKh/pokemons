package com.example.lab3kotlinchat.backend.client

import com.example.lab3kotlinchat.backend.client.entyties.Ability
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.backend.client.entyties.PokemonFirebase
import retrofit2.HttpException

class PokemonClient(
    clientConfig: ClientConfig = ClientConfig()
) : PokeApi {

    private val service = PokeApiServiceImpl(clientConfig)

    //   override fun getPokemon(name: String, watcher: MutableLiveData<Pokemon>) = service.getPokemon(name).result(watcher)

    override suspend fun getPokemon(name: String): Pokemon? {
        var pokemon:Pokemon? = null
        try {
            pokemon = service.getPokemon(name)
        } catch (_: HttpException) {

        }
        return pokemon
    }

    override suspend fun getAbility(id: Int): Ability? {
        var ability:Ability? = null
        try {
            ability = service.getAbility(id)
        } catch (_: HttpException) {

        }
        return ability
    }

    suspend fun getPokemonForFirebase(
        document: PokemonFirebase, pokemon: Pokemon
    ): PokemonFirebase {

        val pF = PokemonFirebase(document.document, document.pokeId, document.dateAdd)
        pF.pokemon = pokemon
        return pF

    }

}
