package com.wajahatkarim3.dino.compose.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class GameState(
    initialScore: Int = 0,
    initialHighScore: Int = 0,
    var isGameOver: Boolean = false
) {

    private val _currentScore: MutableLiveData<Int> = MutableLiveData(initialScore)
    val currentScore: LiveData<Int>
        get() = _currentScore

    private val _highScore: MutableLiveData<Int> = MutableLiveData(initialHighScore)
    val highScore: LiveData<Int>
        get() = _highScore

    fun increaseScore() {
        _currentScore.postValue(requireNotNull(_currentScore.value).inc())
        Log.d("GamteState", "new currentScore:${_currentScore.value}")
    }

    fun replay() {
        val score = requireNotNull(_currentScore.value)
        val high = requireNotNull(_highScore.value)
        _highScore.value = if (score > high) score else high
        _currentScore.value = 0
        isGameOver = false
    }
}