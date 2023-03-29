import com.example.lab3kotlinchat.backend.client.entyties.Pokemon

data class PokemonListResponse(
    val count: Int,
//    val next: String?,
//    val previous: String?,
//    val results: List<PokemonListItem>,
//    val pokemon: List<Pokemon>
)

data class PokemonListItem(
    val name: String,
    val url: String
)