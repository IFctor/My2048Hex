package hu.uni.miskolc.iit.mobile.my2048.core.data.datasource

import hu.uni.miskolc.iit.mobile.my2048.core.domain.Level
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Game

interface GameDataSource {
    suspend fun add(game: Game)
    suspend fun update(game: Game)
    suspend fun remove(game: Game)
    suspend fun fetchById(id: Int) : Game?
    suspend fun fetchAll() : List<Game>
    suspend fun getHighScoreByLevel(level: Level): Int
}