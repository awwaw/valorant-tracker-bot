package repository

import org.example.entities.Player
import org.example.infrastructure.db.tables.references.PLAYER
import org.example.repository.toModel
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class PlayerRepository(
    private val dslContext: DSLContext,
) {
    fun save(player: Player): Player {
        val id =
            dslContext
                .insertInto(PLAYER)
                .set(PLAYER.PUUID, player.puid)
                .set(PLAYER.NAME, player.name)
                .set(PLAYER.TAG, player.tag)
                .set(PLAYER.REGION, player.region.name)
                .set(PLAYER.WINS, player.wins)
                .set(PLAYER.LOSSES, player.losses)
                .returningResult(PLAYER.ID)
                .fetchOne()!!
                .value1()!!
        return player.copy(id = id)
    }

    fun findById(id: Long): Player? =
        dslContext
            .selectFrom(PLAYER)
            .where(PLAYER.ID.eq(id))
            .fetchOne()
            ?.toModel()

    fun update(player: Player) {
        dslContext
            .update(PLAYER)
            .set(PLAYER.WINS, player.wins)
            .set(PLAYER.LOSSES, player.losses)
            .where(PLAYER.ID.eq(player.id))
            .execute()
    }

    fun findByNameAndTag(
        name: String,
        tag: String,
    ): Player? =
        dslContext
            .selectFrom(PLAYER)
            .where(PLAYER.NAME.eq(name).and(PLAYER.TAG.eq(tag)))
            .fetchOne()
            ?.toModel()

    fun delete(id: Long) {
        dslContext
            .deleteFrom(PLAYER)
            .where(PLAYER.ID.eq(id))
            .execute()
    }
}
