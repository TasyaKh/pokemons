package com.example.lab3kotlinchat.ui.gacha

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.lab3kotlinchat.ActivityBag
import com.example.lab3kotlinchat.R
import com.example.lab3kotlinchat.databinding.FragmentGachaBinding
import com.example.lab3kotlinchat.ui.home.GachaViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.runBlocking
import urlToId

class GachaFragment : Fragment() {

    private var _binding: FragmentGachaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gachaViewModel =
            ViewModelProvider(this).get(GachaViewModel::class.java)

        _binding = FragmentGachaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val card = binding.cardOkemon

        binding.catchPokemon.setOnClickListener {
//            val count = gachaViewModel.pokemonCount.value?.count
//            if(  count!=null &&count > 1)
            gachaViewModel.generateRandomPokemon(1, 1009)
        }

//        gachaViewModel.pokemonCount.observe(viewLifecycleOwner) {
//            gachaViewModel.generateRandomPokemon(1, it.count)
//        }


//        если выпал новый покемон
        gachaViewModel.currentPokemon.observe(viewLifecycleOwner) {
//            set name

            card.pokemonName.text = it.name

            card.hp.text = it.stats[0].base_stat.toString()
            card.attack.text = it.stats[1].base_stat.toString()
            card.defence.text = it.stats[2].base_stat.toString()
            card.speed.text = it.stats[5].base_stat.toString()

            var ability = " "
            for (a in it.abilities) {
                ability += a.ability.name + " "

                gachaViewModel.getAbility(urlToId(a.ability.url))

            }

            card.stats.text = ability

            runBlocking {
                val sprites = it.sprites
                if (sprites.front_default != null)
                    Picasso.get().load(sprites.front_default).into(card.imgPokemon)
                else {
                    card.imgPokemon.setImageDrawable(
                        ContextCompat.getDrawable(
                            binding.root.context,
                            R.drawable.baseline_catching_pokemon_24
                        )
                    )
                }
            }
        }

//        сумка, где можно сфоих покемонов смотерть
        binding.bag.setOnClickListener {
//            new activity
            val intent = Intent(binding.root.context, ActivityBag::class.java)
//                intent.putExtra("pokemon" ,pokemon)

//                intent.putExtra("pokemon_name", pokemon.name)
//                intent.putExtra("pokemon_id", pokemon.id)
//                intent.putExtra("pokemon_height", pokemon.height)
//                intent.putExtra("pokemon_weight", pokemon.weight)

            ContextCompat.startActivity(binding.root.context, intent, null)

        }

        gachaViewModel.ability.observe(viewLifecycleOwner) {
//            var txt  = ""
//            if( it.effectEntries!=null){
//                for (ee in it.effectEntries){
//                    txt += ee.effect + "\n"
//                }
//            }
//
//
//            if(txt != "")
//                 binding.stats.text = txt
//            println("txt " + txt)
        }

        gachaViewModel.getPokemonCount()

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
            //your codes here
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}