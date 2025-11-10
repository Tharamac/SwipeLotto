package com.example.swipelotto.logic
import kotlin.math.exp
import kotlin.math.roundToInt

class ScoreCal {

    /**
     * Maps a raw input value (0.0 to x_max) to an output (0 to 255)
     * with exponential growth (slow start, fast finish).
     *
     * @param xRaw The input value in the range [0.0, xMax].
     * @param xMax The maximum value of the input domain (default is 10000.0).
     * @param k The steepness/growth factor (default is 8.0 for a very slow start).
     * @return The resulting value mapped to the range [0, 255].
     */
    fun exponentialMap(xRaw: Double, xMax: Double = 10000.0, k: Double = 8.0): Int {

        // 1. Normalize the input to the [0.0, 1.0] range
        val xNormalized = when {
            xRaw <= 0.0 -> 0.0
            xRaw >= xMax -> 1.0
            else -> xRaw / xMax
        }

        // 2. Apply the exponential mapping formula
        val eToTheKMinus1 = exp(k) - 1.0

        val numerator = exp(k * xNormalized) - 1.0

        val yFloat = 255.0 * (numerator / eToTheKMinus1)

        // 3. Round and clamp the result to the [0, 255] range
        return yFloat.roundToInt().coerceIn(0, 255)
    }
}