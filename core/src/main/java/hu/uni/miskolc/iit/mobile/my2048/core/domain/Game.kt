package hu.uni.miskolc.iit.mobile.my2048.core.domain

import java.util.*

data class Game(val id: Int, val started: Date, val rowNum: Int, val colNum: Int,var score: Int?, var duration: Int?) {
}
