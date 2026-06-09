package repository

import org.example.entities.PlayerSnapshot
import org.example.infrastructure.db.tables.references.PLAYER_SNAPSHOT
import org.example.repository.toModel
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.ZoneOffset

@Repository
class PlayerSnapshotRepository(
    private val dsl: DSLContext,
) {
    fun save(
        snapshot: PlayerSnapshot,
        playerId: Long,
    ) {
        dsl
            .insertInto(PLAYER_SNAPSHOT)
            .set(PLAYER_SNAPSHOT.PLAYER_ID, playerId)
            .set(PLAYER_SNAPSHOT.TAKEN_AT, snapshot.takenAt.atOffset(ZoneOffset.UTC))
            .set(PLAYER_SNAPSHOT.TIER_NAME, snapshot.tierName)
            .set(PLAYER_SNAPSHOT.RR, snapshot.rr)
            .set(PLAYER_SNAPSHOT.ELO, snapshot.elo)
            .set(PLAYER_SNAPSHOT.WINS, snapshot.wins)
            .set(PLAYER_SNAPSHOT.LOSSES, snapshot.losses)
            .execute()
    }

    fun findLatestByPlayerId(playerId: Long): PlayerSnapshot? =
        dsl
            .selectFrom(PLAYER_SNAPSHOT)
            .where(PLAYER_SNAPSHOT.PLAYER_ID.eq(playerId))
            .orderBy(PLAYER_SNAPSHOT.TAKEN_AT.desc())
            .limit(1)
            .fetchOne()
            ?.toModel()
}
