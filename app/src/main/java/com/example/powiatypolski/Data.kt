package com.example.powiatypolski

class Data(val capital : String, val code : String, val wojewodztwo : String, val coordinates : Array<Point>, val offset : Point) {
    var wrongCounter = 0
    var skippedCounter = 0
}