package hu.uni.miskolc.iit.mobile.my2048.framework.db.dao

import androidx.room.*
import hu.uni.miskolc.iit.mobile.my2048.framework.db.entity.GameInfo
import hu.uni.miskolc.iit.mobile.my2048.framework.db.entity.*

@Dao
interface GameDao {
    @Insert
    suspend fun insert(entity: GameEntity)

    @Update
    suspend fun update(entity: GameEntity)

    @Delete
    suspend fun delete(entity: GameEntity)

    @Query("SELECT * FROM game WHERE id = :id")
    suspend fun fetchById(id: Int): GameInfo?

    @Query("SELECT * FROM game")
    suspend fun fetchAll(): List<GameInfo>

    @Query("SELECT * FROM game WHERE rowNum = :rowNum AND colNum = :colNum")
    suspend fun fetchByLevel(rowNum: Int, colNum: Int): List<GameInfo>


}