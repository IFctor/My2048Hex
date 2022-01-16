package hu.uni.miskolc.iit.mobile.my2048.core.domain

enum class Level (val id: Int, val rownum: Int, val colNum: Int, val display: String) {
    FOUR(1,4,4,"4x4"),
    FOUR_FIVE(2,4,5,"4x5"),
    FIVE(3,5,5,"5x5"),
    FIVE_SIX(4,5,6,"5x6"),
    SIX(5,6,6,"6x6"),
    SIX_SEVEN(6,6,7,"6x7"),
    SEVEN(7,7,7,"7x7"),
    SEVEN_EIGHT(8,7,8,"7x8"),
    EIGHT(9,8,8,"8x8"),
    EIGHT_NINE(10,8,9,"8x9"),
    NINE(11,9,9,"9x9"),
    NINE_TEN(12,9,10,"9x10"),
    TEN(13,10,10,"10x10");

}