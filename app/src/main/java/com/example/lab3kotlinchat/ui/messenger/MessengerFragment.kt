package com.example.lab3kotlinchat.ui.messenger

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lab3kotlinchat.backend.client.entyties.*
import com.example.lab3kotlinchat.databinding.FragmentMessengerBinding
import com.example.lab3kotlinchat.ui.CustomRecyclerAdapter
import kotlinx.coroutines.runBlocking

class MessengerFragment : Fragment() {

    private var _binding: FragmentMessengerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val messengerViewModel =
            ViewModelProvider(this).get(MessengerViewModel::class.java)

        _binding = FragmentMessengerBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val spinner: Spinner = binding.planetsSpinner
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter.createFromResource(
//            root.context,
//            R.array.planets_array,
//            android.R.layout.simple_spinner_item
//        ).also { adapter ->
//            // Specify the layout to use when the list of choices appears
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            // Apply the adapter to the spinner
//            spinner.adapter = adapter
//        }
//
//        spinner.onItemSelectedListener = SpinnerActivity(messengerViewModel)

//        recycler view

        val adapter = CustomRecyclerAdapter()
        val recyclerView: RecyclerView = binding.chat
        recyclerView.layoutManager = LinearLayoutManager(root.context)
        recyclerView.adapter = adapter


        messengerViewModel.pokemon.observe(viewLifecycleOwner) {
//            println("life " +     viewLifecycleOwner.lifecycle.currentState)

            if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                messengerViewModel.addMessage(PokemonInfo(pokemon = it))
            }
            adapter.names = messengerViewModel.messages

        }

        messengerViewModel.pExist
//        events
        val getPokemon: ImageButton = binding.getPokemon
        getPokemon.setOnClickListener() {
            val userInput = (binding.userInput.text).toString()
            runBlocking {
                messengerViewModel.getPokemonByName(userInput)
            }
            messengerViewModel.addMessage(userInput)
            adapter.names = messengerViewModel.messages
        }

        return root
    }


//   inner class SpinnerActivity(val messengerViewModel:MessengerViewModel) : Activity(), AdapterView.OnItemSelectedListener {
//
//        override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
//           val item =  parent.getItemAtPosition(pos)
//            when(pos){
//                1->messengerViewModel.typeGP = typeGetPokemon.ID
//                2->messengerViewModel.typeGP = typeGetPokemon.NAME
//            }
//        }
//
//        override fun onNothingSelected(parent: AdapterView<*>) {
//            // Another interface callback
//        }
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        // code block

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}