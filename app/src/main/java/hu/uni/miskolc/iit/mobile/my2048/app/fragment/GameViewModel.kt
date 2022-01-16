package hu.uni.miskolc.iit.mobile.my2048.app.fragment

import android.graphics.Point
import android.os.SystemClock
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Level
import hu.uni.miskolc.iit.mobile.my2048.core.domain.Game
import hu.uni.miskolc.iit.mobile.my2048.core.interactor.GameInteractors
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.absoluteValue

class GameViewModel(private val interactors: GameInteractors) : ViewModel() {

    private lateinit var timer: Timer
    private var initialTime: Long = 0L
    private var elapsedTimInSec: Long = 0L
    private val elapsedTime: MutableLiveData<String> = MutableLiveData()

    private var score = 0
    private var prevScore = 0
    private var highScore: Int = 0
    private val scoreHeader: MutableLiveData<String> = MutableLiveData()

    lateinit var num: Array<Array<Int>>
    private var cards: Array<Array<CardModel>> = Array(1) {
        Array(
            1
        ) { CardModel() }
    }
    private var cardBoard: MutableLiveData<CardBoard> = MutableLiveData()
    private val enableUndo: MutableLiveData<Boolean> = MutableLiveData()
    var isPaused = false

    private lateinit var game: Game
    private val gameOver: MutableLiveData<Boolean> = MutableLiveData()


    init {
        elapsedTime.value = "00:00"

        startTimer()
        initScore()
        cardBoard.postValue(CardBoard(cards))
    }

    fun getElapsedTime(): LiveData<String> = elapsedTime

    fun startTimer() {
        if (!isPaused) {
            initialTime = SystemClock.elapsedRealtime() - initialTime
            timer = Timer()
            timer.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
                    elapsedTimInSec = newValue
                    elapsedTime.postValue(newValue.formatTime())
                }
            }, 1000, 1000)
        }
    }

    fun stopTimer() {
        initialTime = SystemClock.elapsedRealtime() - initialTime
        timer.cancel()
    }

    fun initScore() {
        clearScore()
        addScore(0)
    }

    fun clearScore() {
        score = 0
    }

    fun getScoreHeader(): LiveData<String> = scoreHeader

    fun getScore(): Int = score

    fun addScore(i: Int) {
        prevScore = score
        score += i
        if (score < highScore) {
            scoreHeader.postValue("High score:\n$highScore\n$score")
        } else {
            scoreHeader.postValue("High score:\n$score")
        }
    }

    fun getCardBoard(): LiveData<CardBoard> = cardBoard

    fun getEnableUndo(): LiveData<Boolean> = enableUndo

    fun setGameState(paused: Boolean) {
        isPaused = paused
        isPausedMutable.postValue(paused)

    }

    private var isPausedMutable: MutableLiveData<Boolean> = MutableLiveData()
    fun getPausedMutable(): LiveData<Boolean> = isPausedMutable

    fun getGameOver(): LiveData<Boolean> = gameOver


    fun startGame(level: Level) {

        viewModelScope.launch {
            game = interactors.startGame(level)
            highScore = interactors.actualizeGame.getHighScore(level)

            initCards()
        }
    }

    private fun initCards() {
        cards = Array(game.rowNum) {
            Array(
                game.colNum
            ) { CardModel() }
        }
        num = Array(game.rowNum) {
            Array(
                game.colNum
            ) { 0 }
        }


        for (y in 0 until game.colNum) {
            for (x in 0 until game.rowNum) {
                cards[x][y].num = 0
            }
        }
        addRandomNum()
        addRandomNum()

        cardBoard.postValue(CardBoard(cards))
    }


    private fun addRandomNum() {
        val emptyPoints: MutableList<Point> = ArrayList()
        //Üres kártyák pozíciója
        for (y in 0 until game.colNum) {
            for (x in 0 until game.rowNum) {
                if (cards[x][y].num == 0) {
                    emptyPoints.add(Point(x, y))
                }
            }
        }
        //Véletlenszerűen állítsa be egy üres kártya értékét 2-re vagy 4-re (a valószínűségi arány 9:1)
        val p = emptyPoints.removeAt((Math.random() * emptyPoints.size).toInt())
        cards[p.x][p.y].num = if (Math.random() > 0.1) 2 else 4
    }

    //Mozgás figyelése
    private var startX = 0f
    private var startY = 0f
    private var offsetX = 0f
    private var offsetY = 0f

    fun onTouchListener(view: View, motionEvent: MotionEvent): Boolean {
        if (!isPaused) {
            for (y in 0 until game.colNum) {
                for (x in 0 until game.rowNum) {
                    num[x][y] = cards[x][y].num
                }
            }
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    startX = motionEvent.x
                    startY = motionEvent.y
                }
                MotionEvent.ACTION_UP -> {
                    offsetX = motionEvent.x - startX
                    offsetY = motionEvent.y - startY
                    if (Math.abs(offsetX) > Math.abs(offsetY)) {
                        if (offsetX < -5) {
                            swipeLeft()
                        } else if (offsetX > 5) {
                            swipeRight()
                        }
                    } else {
                        if (offsetY < -5) {
                            swipeUp()
                        } else if (offsetY > 5) {
                            swipeDown()
                        }
                    }

                    enableUndo.postValue(true)
                }
            }
            cardBoard.postValue(CardBoard(cards))
        }
        return true
    }

    private fun swipeUp() {
        var b = false
        //Minden sor (az abszcissza x, az ordináta y)
        for (y in 0 until game.colNum) {
            //Minden oszlop (tekintettel arra, hogy az utolsó oszlopot nem kell összehasonlítani, itt csak 3 oszlop szükséges)
            var x = 0
            while (x < game.rowNum - 1) {

                //Hasonlítsa össze a kártyaértékeket
                for (x1 in x + 1 until game.rowNum) {
                    //Ha a kártya (x1, y) nem üres, hasonlítsa össze (x, y)
                    if (cards[x1][y].num > 0) {
                        //Ha a kártya (x, y) üres, mozgassa (x1, y) balra
                        if (cards[x][y].num == 0) {
                            cards[x][y].num = cards[x1][y].num
                            cards[x1][y].num = 0
                            --x //（x1，y）folytatni kell az összehasonlítást
                            b = true
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            //Kombinálja a kártyákat
                            cards[x][y].num = cards[x][y].num * 2
                            cards[x1][y].num = 0
                            addScore(cards[x][y].num)
                            b = true
                        }
                        //Ha nem üres kártyával találkozik, (x, y) nem kell folytatnia az összehasonlítást
                        break
                    }
                }
                ++x
            }
        }
        //A kártyacsere után véletlenszerűen adj hozzá egy 2-es vagy 4-es értékű kártyát a játék folytatásához
        if (b) {
            afterSwipe()
        }
    }

    private fun swipeDown() {
        var b = false
        for (y in 0 until game.colNum) {
            var x = game.rowNum - 1
            while (x > 0) {
                for (x1 in x - 1 downTo 0) {
                    if (cards[x1][y].num > 0) {
                        if (cards[x][y].num == 0) {
                            cards[x][y].num = cards[x1][y].num
                            cards[x1][y].num = 0
                            ++x
                            b = true
                        } else if (cards[x][y].equals(cards[x1][y])) {
                            cards[x][y].num = cards[x][y].num * 2
                            cards[x1][y].num = 0
                            addScore(cards[x][y].num)
                            b = true
                        }
                        break
                    }
                }
                --x
            }
        }
        if (b) {
            afterSwipe()
        }
    }

    private fun swipeLeft() {
        var b = false
        for (x in 0 until game.rowNum) {
            var y = 0
            while (y < game.colNum - 1) {
                for (y1 in y + 1 until game.colNum) {
                    if (cards[x][y1].num > 0) {
                        if (cards[x][y].num == 0) {
                            cards[x][y].num = cards[x][y1].num
                            cards[x][y1].num = 0
                            --y
                            b = true
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].num = cards[x][y].num * 2
                            cards[x][y1].num = 0
                            addScore(cards[x][y].num)
                            b = true
                        }
                        break
                    }
                }
                ++y
            }
        }
        if (b) {
            afterSwipe()
        }
    }

    private fun swipeRight() {
        var b = false
        for (x in 0 until game.rowNum) {
            var y = game.colNum - 1
            while (y > 0) {
                for (y1 in y - 1 downTo 0) {
                    if (cards[x][y1].num > 0) {
                        if (cards[x][y].num == 0) {
                            cards[x][y].num = cards[x][y1].num
                            cards[x][y1].num = 0
                            ++y
                            b = true
                        } else if (cards[x][y].equals(cards[x][y1])) {
                            cards[x][y].num = cards[x][y].num * 2
                            cards[x][y1].num = 0
                            addScore(cards[x][y].num)
                            b = true
                        }
                        break
                    }
                }
                --y
            }
        }
        if (b) {
            afterSwipe()
        }
    }

    private fun afterSwipe() {
        addRandomNum()
        checkGameOver() //Minden alkalommal, amikor egy 2-es vagy 4-es értékű kártyát adunk hozzá, meg kell határozni, hogy a játéknak vége-e

    }

    private fun checkGameOver() {
        var isOver = true
        ALL@ for (y in 0 until game.colNum) {
            for (x in 0 until game.rowNum) {
                /*A játék folytatásának feltételei:
                 * 1. Legalább egy üres kártya
                 * 2. Nincs üres kártya, de van két szomszédos, azonos értékű kártya
                 */
                if (cards[x][y].num == 0 ||
                    x < game.rowNum - 1 && cards[x][y].equals(cards[x + 1][y]) ||
                    y < game.colNum - 1 && cards[x][y].equals(cards[x][y + 1])
                ) {
                    //Nincs vége, a játék folytatódik
                    isOver = false
                    break@ALL
                }
            }
        }
        //Játék vége
        if (isOver) {
            endGame()
        }
    }

    fun undoGame() {
        viewModelScope.launch {
            for (y in 0 until game.colNum) {
                for (x in 0 until game.rowNum) {
                    cards[x][y].num = num[x][y]
                }
            }
            addScore(-(score - prevScore))
            enableUndo.postValue(false)
            cardBoard.postValue(CardBoard(cards))
        }
    }

    fun pauseGame() {
        viewModelScope.launch {
            setGameState(true)
            stopTimer()
        }
    }

    fun resumeGame() {
        viewModelScope.launch {
            setGameState(false)
            startTimer()
        }
    }

    fun endGame() {
        viewModelScope.launch {
            game.duration = elapsedTimInSec.toInt()
            game.score = score
            interactors.endGame(game)
            timer.cancel()
            gameOver.postValue(true)
        }
    }
}


fun Long.formatTime(): String {
    val minutes = this.absoluteValue / 60
    val seconds = this.absoluteValue % 60
    return String.format("%02d:%02d", minutes, seconds)
}