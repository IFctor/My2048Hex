package hu.uni.miskolc.iit.mobile.my2048.framework.db.datasource

import hu.uni.miskolc.iit.mobile.my2048.framework.db.dao.GameDao
import hu.uni.miskolc.iit.mobile.my2048.framework.db.mapper.GameMapper
import hu.uni.miskolc.iit.mobile.my2048.core.data.datasource.GameDataSource
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Level
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Game

class RoomGameDataSource(val dao: GameDao, val mapper: GameMapper) : GameDataSource {

    override suspend fun add(game: Game) {
        dao.insert(mapper.mapToEntity(game))
    }

    override suspend fun update(game: Game) = dao.update(mapper.mapToEntity(game))

    override suspend fun remove(game: Game) = dao.delete(mapper.mapToEntity(game))

    override suspend fun fetchById(id: Int): Game? = dao.fetchById(id)?.let { mapper.mapFromEntity(it) }

    override suspend fun fetchAll(): List<Game> = dao.fetchAll().map { mapper.mapFromEntity(it) }

    override suspend fun getHighScoreByLevel(level: Level): Int =
        dao.fetchByLevel(level.rownum, level.colNum).map { mapper.mapFromEntity(it).score ?:0 }.maxOrNull() ?:0
}