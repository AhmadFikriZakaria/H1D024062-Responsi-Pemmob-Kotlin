package com.example.pokedexkanto.data.model

import com.google.gson.annotations.SerializedName

// ===== Response dari GET /pokemon?limit=151 =====

data class PokemonListResponse(
    val count: Int,
    val results: List<PokemonResult>
)

// Endpoint daftar hanya mengirim nama dan url, tanpa id dan gambar
data class PokemonResult(
    val name: String,
    val url: String
)

// ===== Response dari GET /pokemon/{id} =====

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int, // satuan desimeter
    val weight: Int, // satuan hektogram
    @SerializedName("base_experience")
    val baseExperience: Int?,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>,
    val abilities: List<AbilitySlot>,
    val sprites: Sprites
)

data class TypeSlot(
    val slot: Int,
    val type: NamedResource
)

data class StatSlot(
    @SerializedName("base_stat")
    val baseStat: Int,
    val stat: NamedResource
)

data class AbilitySlot(
    val ability: NamedResource,
    @SerializedName("is_hidden")
    val isHidden: Boolean
)

data class NamedResource(
    val name: String,
    val url: String
)

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String?,
    val other: OtherSprites?
)

data class OtherSprites(
    @SerializedName("official-artwork")
    val officialArtwork: Artwork?
)

data class Artwork(
    @SerializedName("front_default")
    val frontDefault: String?
)
