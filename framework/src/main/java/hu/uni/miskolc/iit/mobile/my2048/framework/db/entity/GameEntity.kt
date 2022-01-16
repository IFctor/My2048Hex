package hu.uni.miskolc.iit.mobile.my2048.framework.db.entity

import androidx.room.*
import java.util.*

@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey
    val id: Int,
    val started: Date,
    val rowNum: Int,
    val colNum: Int,
    val score: Int,
    val duration: Int,
)

data class GameInfo(
    @Embedded
    val entity: GameEntity
)