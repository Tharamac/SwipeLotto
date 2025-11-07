package com.example.swipelotto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform