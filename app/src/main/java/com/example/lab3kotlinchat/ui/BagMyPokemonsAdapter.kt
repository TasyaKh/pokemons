package com.example.lab3kotlinchat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.lab3kotlinchat.R
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon
import com.example.lab3kotlinchat.backend.client.entyties.PokemonFirebase
import com.example.lab3kotlinchat.backend.client.entyties.PokemonInfo
import com.example.lab3kotlinchat.databinding.CardOfPokemonBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking


class BagMyPokemonsAdapter() :
    RecyclerView.Adapter<ListMyPokemonsHolder>() {


    private var _controlPointEl = MutableLiveData<Pair<Int, Int>>(Pair(0, 20))
    private var controlEndPointEl = 20

    fun getControlPointEl(): MutableLiveData<Pair<Int, Int>> {
        return _controlPointEl
    }

    // хранит покемонов и сообщения юзера
    var pokemons = ArrayList<PokemonFirebase>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun addPokemon(p: PokemonFirebase) {
        pokemons.add(p)
        notifyItemChanged(pokemons.size)
//        pokemons.sortBy { pk -> pk.id }
//        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMyPokemonsHolder {

        return when (viewType) {
            R.layout.card_of_pokemon -> ListMyPokemonsHolder.MyPokemonViewHolder(
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


        println("position " + position + "     controlEndPointEl" + controlEndPointEl)

        val pos = position + 1
        if (controlEndPointEl <= pos) {
            controlEndPointEl *= 2
            _controlPointEl.value = Pair(pos + 1, controlEndPointEl)

            println("controlPointEl " + controlEndPointEl)
        }


        when (holder) {
            is ListMyPokemonsHolder.MyPokemonViewHolder -> holder.bind(pokemons[position] as PokemonFirebase)
            else -> {}
        }
    }

    override fun getItemCount() = pokemons.size

    //    его автоматически вызывает onCreateViewHolder
    override fun getItemViewType(position: Int): Int {
        return R.layout.card_of_pokemon
    }
}

sealed class ListMyPokemonsHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class MyPokemonViewHolder(private val binding: CardOfPokemonBinding) :
        ListMyPokemonsHolder(binding) {

        private lateinit var pokemonInfo: PokemonInfo

        fun bind(p: PokemonFirebase) {

            val pokemon = p.pokemon

            if (pokemon != null) {
                binding.pokemonName.text = pokemon.name

                binding.hp.text = pokemon.stats[0].base_stat.toString()
                binding.attack.text = pokemon.stats[1].base_stat.toString()
                binding.defence.text = pokemon.stats[2].base_stat.toString()
                binding.speed.text = pokemon.stats[5].base_stat.toString()

                runBlocking {
                    val sprites = pokemon.sprites
                    if (sprites.front_default != null)
                        Picasso.get().load(sprites.front_default).into(binding.imgPokemon)
                    else {
                        binding.imgPokemon.setImageDrawable(
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
}

