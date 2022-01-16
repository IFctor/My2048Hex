package hu.uni.miskolc.iit.mobile.my2048.app.fragment

import android.graphics.Color

class CardModel {
    var text = ""
    var bgColor = Color.GRAY
    var num = 0
        set(num) {

            field = num
            if (num > 0) {
                text = Integer.toHexString(num)
            } else {
                text = ""
            }
            when (num) {
                0 -> bgColor = -0x333f4e
                2 -> bgColor = -0x111b26
                4 -> bgColor = -0x121f38
                8 -> bgColor = -0xd4e87
                16 -> bgColor = -0xa6a9d
                32 -> bgColor = -0x983a1
                64 -> bgColor = -0x9a1c5
                128 -> bgColor = -0x12308e
                256 -> bgColor = -0x1238b0
                512 -> bgColor = -0x1237b0
                1024 -> bgColor = -0x1339c0
                2048 -> bgColor = 0x1339c0
                4096 -> bgColor = Color.GREEN
                8192 -> bgColor = Color.RED
                else -> bgColor = Color.GRAY
            }
        }


    fun equals(card: CardModel): Boolean {
        return num == card.num
    }

}