package com.example.lab3kotlinchat.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.lab3kotlinchat.R
import com.example.lab3kotlinchat.backend.client.entyties.*
import com.example.lab3kotlinchat.databinding.MyMessageBinding
import com.example.lab3kotlinchat.databinding.PokemonMsgBinding
import com.example.lab3kotlinchat.db.DBConnection
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking

class CustomRecyclerAdapter() :
    RecyclerView.Adapter<CustomRecyclerHolder>() {

    // хранит покемонов и сообщения юзера
    var names = ArrayList<Any>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomRecyclerHolder {

        return when (viewType) {

            R.layout.pokemon_msg -> CustomRecyclerHolder.PokemonViewHolder(
                PokemonMsgBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.my_message -> CustomRecyclerHolder.UserViewHolder(
                MyMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid ViewType Provided, viewType: $viewType")
        }
    }

    override fun onBindViewHolder(holder: CustomRecyclerHolder, position: Int) {
        when (holder) {
            is CustomRecyclerHolder.PokemonViewHolder -> holder.bind(names[position] as PokemonInfo)
            is CustomRecyclerHolder.UserViewHolder -> holder.bind(names[position] as String)
            else -> {}
        }
    }

    override fun getItemCount() = names.size

    //    его автоматически вызывает onCreateViewHolder
    override fun getItemViewType(position: Int): Int {
        return when (names[position]) {
            is PokemonInfo -> R.layout.pokemon_msg
            is String -> R.layout.my_message
            else -> {
                println(" names[position]  " + names[position])
                0
            }
        }
    }
}

sealed class CustomRecyclerHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class PokemonViewHolder(private val binding: PokemonMsgBinding) :
        CustomRecyclerHolder(binding) {

        fun bind(pokemonInfo: PokemonInfo) {

            val pokemon = pokemonInfo.pokemon
            val abilities: StringBuilder = StringBuilder("abilities: ")
            pokemon.abilities.forEach { abilities.append("${it.ability.name} ") }

            binding.size.text = pokemonInfo.size()
            binding.name.text = pokemon.name
            binding.number.text = pokemon.id.toString()
            binding.ability.text = abilities

            val db = DBConnection()

            setFavorite(db, pokemon.id)

            binding.favorite.setOnCheckedChangeListener { buttonView, isChecked ->
                run {

                    if (isChecked)
                        db.addFavoritePokemon(pokemon)
                    else{
                        db.removeFavoritePokemon(pokemon.id)
                    }
                }
            }

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

            binding.msgLayout.setOnClickListener {
                pokemonInfo.seeFullInfo = !pokemonInfo.seeFullInfo
                changePokemonMsg(binding, pokemonInfo)
            }

            changePokemonMsg(binding, pokemonInfo)

        }


        private fun setFavorite(db:DBConnection, id:Int){

            db.getFavoritePokemon(id).addOnCompleteListener { task ->
                var res = false
                if (task.isSuccessful) {
                    val doc = task.result
                    if (doc.exists()) {
                        res = true
                    }
                }

                binding.favorite.isChecked = res

                Log.d("getFavoritePokemon", " res $res")
            }



        }
    }




    fun changePokemonMsg(binding: PokemonMsgBinding, pokemonInfo: PokemonInfo) {

        val makeVisibleViceVersa = { view: View, visibility: Int ->
            view.visibility = visibility
        }

        if (pokemonInfo.seeFullInfo) {
            makeVisibleViceVersa(binding.size, View.VISIBLE)
            makeVisibleViceVersa(binding.name, View.VISIBLE)
            makeVisibleViceVersa(binding.number, View.VISIBLE)

            binding.pokeImg.alpha = 0.4F
            binding.msgLayout.background =
                ContextCompat.getDrawable(binding.root.context, R.drawable.their_message)


        } else {
            makeVisibleViceVersa(binding.size, View.INVISIBLE)
            makeVisibleViceVersa(binding.name, View.INVISIBLE)
            makeVisibleViceVersa(binding.number, View.INVISIBLE)

            binding.pokeImg.alpha = 1F
            binding.msgLayout.background =
                ContextCompat.getDrawable(binding.root.context, R.color.white)

        }
    }

    class UserViewHolder(private val binding: MyMessageBinding) : CustomRecyclerHolder(binding) {
        fun bind(text: String) {
            binding.messageBody.text = text
        }
    }
}

