package com.example.lab3kotlinchat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.lab3kotlinchat.R
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.backend.client.entyties.PokemonInfo
import com.example.lab3kotlinchat.databinding.PokemonBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking

class ListAllPokemonsAdapter() :
    RecyclerView.Adapter<ListPokemonsHolder>() {


    private var _controlPointEl = MutableLiveData<Pair<Int,Int>>( Pair(0,20))
    private var controlEndPointEl = 20

    fun getControlPointEl(): MutableLiveData<Pair<Int, Int>> {
        return _controlPointEl
    }

    // хранит покемонов и сообщения юзера
    var pokemons = ArrayList<Pokemon>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addPokemon(p: Pokemon) {
        pokemons.add(p)
        pokemons.sortBy { pk -> pk.id }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListPokemonsHolder {

        return when (viewType) {
            R.layout.pokemon -> ListPokemonsHolder.PokemonViewHolder(
                PokemonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided, viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ListPokemonsHolder, position: Int) {

//        val  newControlPointEl = position


        println("position " + position +  "     controlEndPointEl" + controlEndPointEl)

        val pos = position + 1
        if( controlEndPointEl<=pos){
            controlEndPointEl *= 2
            _controlPointEl.value = Pair(pos+1, controlEndPointEl)

            println("controlPointEl " + controlEndPointEl)
        }


        when (holder) {
            is ListPokemonsHolder.PokemonViewHolder -> holder.bind(pokemons[position] as Pokemon)
            else -> {}
        }
    }

    override fun getItemCount() = pokemons.size

    //    его автоматически вызывает onCreateViewHolder
    override fun getItemViewType(position: Int): Int {
        return R.layout.pokemon
    }
}

sealed class ListPokemonsHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class PokemonViewHolder(private val binding: PokemonBinding) :
        ListPokemonsHolder(binding) {

        private lateinit var pokemonInfo:PokemonInfo

        fun bind(pokemon: Pokemon) {
            pokemonInfo = PokemonInfo(pokemon)
            val abilities: StringBuilder = StringBuilder("abilities: ")

            pokemon.abilities.forEach { abilities.append(it.ability.name + " ") }

            binding.size.text = pokemonInfo.size()
            binding.name.text = pokemon.name
            binding.number.text = pokemon.id.toString()
            binding.ability.text = abilities

            runBlocking {
                val sprites = pokemon.sprites
                if (sprites.front_default != null)
                    Picasso.get().load(sprites.front_default).into(binding.pokeImg)
                else {
                    binding.pokeImg.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.baseline_catching_pokemon_24
                        )
                    )
                }
            }
        }
    }
}

