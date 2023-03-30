package com.example.lab3kotlinchat.ui.gacha

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
            gachaViewModel.generateRandomPokemon(1, 1009)
        }


//        если выпал новый покемон
        gachaViewModel.currentPokemon.observe(viewLifecycleOwner) {

            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {

//            set pokemon in card
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

                gachaViewModel.saveMyPokemon(it)
            }


        }

//        сумка, где можно сфоих покемонов смотерть
        binding.bag.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_gacha_to_navigation_bag)
        }

//        gachaViewModel.ability.observe(viewLifecycleOwner) {
////            var txt  = ""
////            if( it.effectEntries!=null){
////                for (ee in it.effectEntries){
////                    txt += ee.effect + "\n"
////                }
////            }
////
////
////            if(txt != "")
////                 binding.stats.text = txt
////            println("txt " + txt)
//        }

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