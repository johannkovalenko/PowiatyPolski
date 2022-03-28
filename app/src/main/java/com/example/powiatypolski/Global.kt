package com.example.powiatypolski

object Global {
    val granularity = 2

    fun calc(point : Int, offset : Int) : Int {
        return ((point / 1.15 + offset / 2) / granularity).toInt() * granularity
    }

    fun calc(point : Float) : Int {
        return point.toInt() / granularity * granularity
    }
}