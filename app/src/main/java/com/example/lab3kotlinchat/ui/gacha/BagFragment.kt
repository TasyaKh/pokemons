package com.example.lab3kotlinchat.ui.gacha

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3kotlinchat.databinding.FragmentBagBinding
import com.example.lab3kotlinchat.ui.BagMyPokemonsAdapter

class BagFragment : Fragment() {

    private var _binding: FragmentBagBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bagViewModel =
            ViewModelProvider(this).get(BagViewModel::class.java)

        _binding = FragmentBagBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = BagMyPokemonsAdapter()
        val recyclerView: RecyclerView = binding.listPokemons
        recyclerView.adapter = adapter

        bagViewModel.getMyPokemons()
        bagViewModel.pokemonFirebase.observe(viewLifecycleOwner) {
            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                println("pokeeeemon " + it.pokemon)
                adapter.addPokemon(it)
                bagViewModel.getPokemon()
            }
        }

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        val SDK_INT = Build.VERSION.SDK_INT
//        if (SDK_INT > 8) {
//            val policy = StrictMode.ThreadPolicy.Builder()
//                .permitAll().build()
//            StrictMode.setThreadPolicy(policy)
//            //your codes here
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}