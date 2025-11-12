package com.example.swipelotto.ui

data class LottoState (
    val lottoNumber:  String = "123456",
    val digitsWithScore: List<Int> = List<Int>(6, {0}) ,
) {

}