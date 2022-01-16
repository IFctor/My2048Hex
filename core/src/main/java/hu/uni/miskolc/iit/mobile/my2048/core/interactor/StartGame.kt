package hu.uni.miskolc.iit.mobile.my2048.core.interactor

import android.os.SystemClock
import hu.uni.miskolc.iit.mobile.my2048.core.data.repository.GameRepository
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Level
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Game
import java.util.*

class StartGame(
    private val gameRepository: GameRepository
    ) {

    suspend operator fun invoke(level: Level) : Game {

        val newGame = Game((SystemClock.elapsedRealtime()/1000.0).toInt(), Date(), level.rownum, level.colNum, null,null)
        gameRepository.add(newGame)
        gameRepository
        return newGame
    }
}