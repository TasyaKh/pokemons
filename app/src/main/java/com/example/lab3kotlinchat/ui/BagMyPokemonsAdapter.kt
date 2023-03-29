package com.example.lab3kotlinchat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.lab3kotlinchat.R
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.backend.client.entyties.PokemonInfo
import com.example.lab3kotlinchat.databinding.CardOfPokemonBinding


class BagMyPokemonsAdapter() :
    RecyclerView.Adapter<ListMyPokemonsHolder>() {


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMyPokemonsHolder {

        return when (viewType) {
            R.layout.card_of_pokemon -> ListMyPokemonsHolder.PokemonViewHolder(
                CardOfPokemonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid ViewType Provided, viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: ListMyPokemonsHolder, position: Int) {

//        val  newControlPointEl = position


        println("position " + position +  "     controlEndPointEl" + controlEndPointEl)

        val pos = position + 1
        if( controlEndPointEl<=pos){
            controlEndPointEl *= 2
            _controlPointEl.value = Pair(pos+1, controlEndPointEl)

            println("controlPointEl " + controlEndPointEl)
        }


        when (holder) {
            is ListMyPokemonsHolder.PokemonViewHolder -> holder.bind(pokemons[position] as Pokemon)
            else -> {}
        }
    }

    override fun getItemCount() = pokemons.size

    //    его автоматически вызывает onCreateViewHolder
    override fun getItemViewType(position: Int): Int {
        return R.layout.pokemon
    }
}

sealed class ListMyPokemonsHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class PokemonViewHolder(private val binding: CardOfPokemonBinding) :
        ListMyPokemonsHolder(binding) {

        private lateinit var pokemonInfo:PokemonInfo

        fun bind(pokemon: Pokemon) {

        }
    }
}

