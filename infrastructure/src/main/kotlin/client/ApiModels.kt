package org.example.client

import com.fasterxml.jackson.annotation.JsonProperty

data class StoredMatchesResponse(
    val data: List<StoredMatchDto>,
)

data class StoredMatchDto(
    val meta: MatchMetaDto,
    val stats: MatchStatsDto,
    val teams: MatchTeamsDto,
)

data class MatchMetaDto(
    val id: String,
    val map: MapDto,
    val mode: String,
    @param:JsonProperty("started_at") val startedAt: String,
)

data class MapDto(
    val name: String,
)

data class MatchStatsDto(
    val team: String,
    val character: CharacterDto,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
)

data class CharacterDto(
    val name: String,
)

data class MatchTeamsDto(
    val red: Int,
    val blue: Int,
)

// --- MMR ---

data class MmrResponse(
    val data: MmrDataDto,
)

data class AccountDto(
    val puuid: String,
    val name: String,
    val tag: String,
)

data class CurrentRankDto(
    val tier: TierDto,
    val rr: Int,
    val elo: Int,
)

data class MmrDataDto(
    val account: AccountDto,
    val current: CurrentRankDto,
    val seasonal: List<SeasonalDto>,
)

data class SeasonalDto(
    val wins: Int,
    val games: Int,
)

data class TierDto(
    val name: String,
)
