package hu.uni.miskolc.iit.mobile.my2048.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import hu.uni.miskolc.iit.mobile.my2048.framework.db.entity.GameEntity
import hu.uni.miskolc.iit.mobile.my2048.framework.db.converter.DateTypeConverter
import hu.uni.miskolc.iit.mobile.my2048.framework.db.converter.LevelTypeConverter
import hu.uni.miskolc.iit.mobile.my2048.framework.db.dao.GameDao

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateTypeConverter::class,
    LevelTypeConverter::class
)
abstract class GameDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "2048.db"
        private var instance: GameDatabase? = null

        private fun create(context: Context) : GameDatabase = Room.databaseBuilder(context, GameDatabase::class.java, DATABASE_NAME)
            .addCallback(DB_CALLBACK)
            .build()

        fun getInstance(context: Context) : GameDatabase = (instance ?: create(context)).also { instance = it }

        private val DB_CALLBACK = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }
        }

    }

    abstract fun gameDao() : GameDao
}