package org.example.repository

import org.example.entities.Player
import org.example.infrastructure.db.tables.records.PlayerRecord
import org.example.infrastructure.db.tables.references.PLAYER
import org.jooq.DSLContext
import org.jooq.impl.DSL

class PlayerRepository(
    private val dslContext: DSLContext
) {
    fun save(player: Player): Player? {
        val created = dslContext.insertInto(PLAYER)
            .set(PLAYER.ID, player.id)
            .set(PLAYER.PUUID, player.puid.toString())
            .set(PLAYER.NAME, player.name)
            .set(PLAYER.TAG, player.tag)
            .set(PLAYER.REGION, player.region.name)
            .set(PLAYER.WINS, player.wins)
            .set(PLAYER.LOSSES, player.losses)
            .execute()
        if (created != 0) {
            return player
        }
        return null
    }

    fun findById(id: Long): Player? {
        return dslContext.selectFrom(PLAYER)
            .where(PLAYER.ID.eq(id))
            .fetchOne()
            ?.toModel()
    }


    fun PlayerRecord.toModel(): Player = Player(
        id = this.id!!,
        puid = this.puuid!!,
        name = this.name!!,
        tag = this.tag!!,
        region = PlayerRegion.from(this.region!!),
        wins = this.wins!!,
        losses = this.losses!!
    )
}