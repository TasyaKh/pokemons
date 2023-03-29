package com.example.lab3kotlinchat.ui.home

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3kotlinchat.databinding.FragmentHomeBinding
import com.example.lab3kotlinchat.ui.ListAllPokemonsAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val adapter = ListAllPokemonsAdapter()
        val recyclerView: RecyclerView = binding.pokemons
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = adapter


        homeViewModel.pokemon.observe(viewLifecycleOwner) {
            adapter.addPokemon(it)
        }

        homeViewModel.pokemonCount.observe(viewLifecycleOwner) {
//            println(" rlkmkvmokdmfv " + it.count)
            homeViewModel.getPokemons(it.count)
        }

        adapter.getControlPointEl().observe(viewLifecycleOwner) {
//            println(" rlkmkvmokdmfv " + it.count)
            homeViewModel.getPokemons(it.first, it.second)
        }


        println(" helllooooooo ")
        homeViewModel.getPokemonCount()

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