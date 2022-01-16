package hu.uni.miskolc.iit.mobile.my2048.core.data.repository

import hu.uni.miskolc.iit.mobile.my2048.core.data.datasource.GameDataSource
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Level
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Game

class GameRepository(private val dataSource: GameDataSource) {
    suspend fun add(game: Game) = dataSource.add(game)
    suspend fun update(game: Game) = dataSource.update(game)
    suspend fun remove(game: Game) = dataSource.remove(game)
    suspend fun fetchById(id: Int) : Game? = dataSource.fetchById(id)
    suspend fun fetchAll() : List<Game> = dataSource.fetchAll()
    suspend fun getHighScoreByLevel(level: Level): Int = dataSource.getHighScoreByLevel(level)
}