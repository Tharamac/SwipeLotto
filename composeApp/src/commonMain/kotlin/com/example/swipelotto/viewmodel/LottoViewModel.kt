package com.example.swipelotto.viewmodel

import androidx.lifecycle.ViewModel
import com.example.swipelotto.dataclass.NumberWithScore
import com.example.swipelotto.util.ScoreCal


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.Double
import kotlin.collections.MutableList
import kotlin.math.abs
import kotlin.random.Random

// Game UI state

class LottoViewModel: ViewModel() {
//    private lateinit var randomLottoNumber: CharArray

    private lateinit var digitsSwipeScore: MutableList<Double>
    // Game UI state
    private val _uiState = MutableStateFlow(LottoState())
    // Backing property to avoid state updates from other classes (It means read-only)
    val uiState: StateFlow<LottoState> = _uiState.asStateFlow()

    init {
        reset()
        digitsSwipeScore = MutableList<Double>(6, {0.0})
    }
    fun reset(){
        _uiState.value = LottoState(
            lottoNumber = randomLotto()
        )
    }
    private fun randomLotto(): String{
        return Random.nextInt(1000000).toString().padStart(6, '0')
    }

    private fun setAlpha(numberWithScore: NumberWithScore, addScore: Int): NumberWithScore{
        if(addScore >= 255){
            return  NumberWithScore(digit = numberWithScore.digit, alpha = 255)
        }
        return NumberWithScore(digit = numberWithScore.digit, alpha = addScore)
    }

    fun onDrag(x:Float, y: Float){
            val absX = abs(x)
            val absY = abs(y)

//           println("${absX}, ${absY}, mean: ${(absX + absY) / 2}, vector: ${sqrt(x.pow(2) + y.pow(2))}")
            val rand = Random.nextInt(6)
            digitsSwipeScore[rand]  += (absX + absY) / 2
            val alpha = ScoreCal().exponentialMap( digitsSwipeScore[rand])
            val newList = uiState.value.digitsWithScore.toMutableList()

            if(alpha >= 255) {
                newList[rand] = 255
            }else {
                newList[rand] = alpha
            }

            _uiState.update{
                state -> state.copy(
                    digitsWithScore = newList.toList()
                )
            }
    }
}

