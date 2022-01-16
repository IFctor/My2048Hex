package hu.uni.miskolc.iit.mobile.my2048.framework.db.converter

import androidx.room.TypeConverter
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Level

class LevelTypeConverter {
    @TypeConverter
    fun toLevel(value: Int) : Level = Level.values().first { it.id == value }

    @TypeConverter
    fun toIntValue(level: Level) : Int = level.id
}