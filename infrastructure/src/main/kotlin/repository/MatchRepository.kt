package org.example.repository

import generated.db.tables.references.MATCH
import org.example.entities.Match
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime
import java.time.ZoneOffset
import kotlin.time.Instant
import kotlin.time.toJavaInstant

@Repository
class MatchRepository(
    private val dsl: DSLContext,
) {
    fun save(
        match: Match,
        playerId: Long,
    ) {
        dsl
            .insertInto(MATCH)
            .set(MATCH.ID, match.id)
            .set(MATCH.PLAYER_ID, playerId)
            .set(MATCH.PLAYED_AT, match.playedAt.atOffset(ZoneOffset.UTC))
            .set(MATCH.MAP, match.map)
            .set(MATCH.QUEUE_ID, match.queueId)
            .set(MATCH.AGENT, match.stats.agent)
            .set(MATCH.KILLS, match.stats.kills)
            .set(MATCH.DEATHS, match.stats.deaths)
            .set(MATCH.ASSISTS, match.stats.assists)
            .set(MATCH.WON, match.stats.won)
            .onConflictDoNothing()
            .execute()
    }

    fun findLatestByPlayerId(playerId: Long): Match? =
        dsl
            .selectFrom(MATCH)
            .where(MATCH.PLAYER_ID.eq(playerId))
            .orderBy(MATCH.PLAYED_AT.desc())
            .limit(1)
            .fetchOne()
            ?.toModel()

    fun findByPlayerIdAfter(
        playerId: Long,
        after: Instant,
    ): List<Match> =
        dsl
            .selectFrom(MATCH)
            .where(
                MATCH.PLAYER_ID
                    .eq(playerId)
                    .and(MATCH.PLAYED_AT.gt(OffsetDateTime.ofInstant(after.toJavaInstant(), ZoneOffset.UTC))),
            ).orderBy(MATCH.PLAYED_AT.desc())
            .fetch()
            .map { it.toModel() }
}
