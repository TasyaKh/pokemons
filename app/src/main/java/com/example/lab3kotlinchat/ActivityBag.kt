package com.example.lab3kotlinchat

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import com.example.lab3kotlinchat.backend.client.entyties.Pokemon


class ActivityBag : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pokemon)


        val pokemon_name = intent.getStringExtra("pokemon_name")
        val pokemon_id = intent.getIntExtra("pokemon_id", 0)
        val pokemon_height = intent.getIntExtra("pokemon_height", 0)
        val pokemon_weight = intent.getIntExtra("pokemon_weight", 0)

        val bundle = intent.extras

        val number = findViewById<TextView>( R.id.number)
        val name = findViewById<TextView>( R.id.name)
        val size = findViewById<TextView>( R.id.size)

        if ( intent.extras != null) {
            val pokemon: Pokemon? = bundle!!.getSerializable("pokemon") as Pokemon?
            if(pokemon!=null){
                number.text =pokemon.id.toString()
            }


        }
        number.text =pokemon_id.toString()
        name.text = pokemon_name.toString()
        val  txt =  (" ${pokemon_height}  \n ${pokemon_weight}")
        size.text = txt
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf( R.id.navigation_messenger,
//                R.id.navigation_home
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}