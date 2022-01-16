package hu.uni.miskolc.iit.mobile.my2048.core.interactor

import hu.uni.miskolc.iit.mobile.my2048.core.data.repository.GameRepository
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Game

class EndGame(
    private val gameRepository: GameRepository
) {
    suspend operator fun invoke(game: Game) = gameRepository.update(game)
}