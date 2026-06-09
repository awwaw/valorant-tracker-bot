package org.example.entities

enum class PlayerRegion {
    EU,
    NA,
    LATAM,
    BR,
    AP,
    KR,
    ;

    companion object {
        fun from(region: String): PlayerRegion =
            entries.find { it.name.equals(region, ignoreCase = true) }
                ?: error("Unknown region: $region")
    }
}

data class Player(
    val id: Long,
    val puid: String,
    val name: String,
    val tag: String,
    val region: PlayerRegion,
    val wins: Int = 0,
    val losses: Int = 0,
)
