package hu.uni.miskolc.iit.mobile.my2048.framework.db.mapper

import hu.uni.miskolc.iit.mobile.my2048.framework.db.entity.GameEntity
import hu.uni.miskolc.iit.mobile.my2048.framework.db.entity.GameInfo
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Game

class GameMapper {

    fun mapToEntity(data: Game): GameEntity = GameEntity(
        data.id,
        data.started,
        data.rowNum,
        data.colNum,
        data.score ?: 0,
        data.duration ?: 0
    )

    fun mapFromEntity(info: GameInfo): Game =
        Game(
            info.entity.id,
            info.entity.started,
            info.entity.rowNum,
            info.entity.colNum,
            info.entity.score,
            info.entity.duration
        )

}