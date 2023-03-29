package com.example.lab3kotlinchat.backend.client

import PokemonListResponse
import androidx.lifecycle.MutableLiveData
import com.example.lab3kotlinchat.backend.client.entyties.Ability
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonClient(
    clientConfig: ClientConfig = ClientConfig()
): PokeApi {

    private fun <T> Call<T>.result(watcher: MutableLiveData<T>){

       val call= object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                println("response  $response")
                if (response.isSuccessful) {
                    val resp = response.body()
                    watcher.value = resp
                    println("response server " + resp)
                } else {
                    // handle error
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {

            }
        }
        enqueue(call)
    }
    //  Create Retrofit instance with Gson converter factory
//    private val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl("https://pokeapi.co/api/v2/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    val apiService = retrofit.create(Controller::class.java)
    private val service = PokeApiServiceImpl(clientConfig)

   override fun getPokemon(name: String, watcher: MutableLiveData<Pokemon>) = service.getPokemon(name).result(watcher)

////        println("name of pokemon  $name")
//        val call = service.getPokemonByName(name)
//
//        val callback = object : Callback<Pokemon> {
//
//            override fun onResponse(call: Call<Pokemon>, response: Response<Pokemon>) {
//                println("response  $response")
//                if (response.isSuccessful) {
//                    val pokemon = response.body()
//                    _pokemon.value = pokemon ?: Pokemon(
//                        "fail to get pokemon $name ", 0, 0, 0,
//                        Sprites(""), null
//                    )
//                } else {
//                    // handle error
//                }
//            }
//
//            override fun onFailure(call: Call<Pokemon>, t: Throwable) {
//                _pokemon.value = Pokemon(
//                    "fail to get pokemon $name " + t.message
//                )
//            }
//        }
//
//        call.enqueue(callback)



    fun getPokemonList(pokemonCount: MutableLiveData<PokemonListResponse>): Call<PokemonListResponse> {

        val call = service.getPokemonCount()
        val callback = object : Callback<PokemonListResponse> {
            override fun onResponse(call: Call<PokemonListResponse>, response: Response<PokemonListResponse>) {
                pokemonCount.value =  response.body()
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {

            }
        }

        call.enqueue(callback)

        return service.getPokemonCount()
    }

    override fun getAbility(id: Int, watcher: MutableLiveData<Ability>) = service.getAbility(id).result(watcher)

}
