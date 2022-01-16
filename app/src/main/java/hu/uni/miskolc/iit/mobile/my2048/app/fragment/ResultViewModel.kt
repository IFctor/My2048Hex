package hu.uni.miskolc.iit.mobile.my2048.app.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ResultViewModel : ViewModel() {

    private val score: MutableLiveData<Int> = MutableLiveData()

    fun getScore() : LiveData<Int> = score

    fun setScore(value: Int) {
        score.postValue(value)
    }
}