package com.example.powiatypolski

import android.graphics.Bitmap
import android.graphics.Color
import com.example.powiatypolski.input.Wojewodztwa
import com.google.android.material.snackbar.Snackbar

class Coordinates
{
    private val coordinates : Map<String, Map<String, Data>> = mapOf(
            "małopolskie" to Wojewodztwa.Malopolska(),
            "podkarpackie" to Wojewodztwa.Podkarpackie() ,
            "świętokrzyskie" to Wojewodztwa.Świętokrzyskie(),
            "śląskie" to Wojewodztwa.Śląskie(),
            "opolskie" to Wojewodztwa.Opolskie(),
            "dolnośląskie" to Wojewodztwa.Dolnośląskie(),
            "lubuskie" to Wojewodztwa.Lubuskie(),
            "zachodniopomorskie" to Wojewodztwa.Zachodniopomorskie(),
            "pomorskie" to Wojewodztwa.Pomorskie(),
            "warmińsko-mazurskie" to Wojewodztwa.Warmińsko_mazurskie(),
            "podlaskie" to Wojewodztwa.Podlaskie(),
            "lubelskie" to Wojewodztwa.Lubelskie(),
            "kujawsko-pomorskie" to Wojewodztwa.Kujawsko_pomorskie(),
            "łódzkie" to Wojewodztwa.Łódzkie(),
            "wielkopolskie" to Wojewodztwa.Wielkopolskie(),
            "mazowieckie" to Wojewodztwa.Mazowieckie(),
    )
    private val shuffled : MutableMap<String, MutableList<String>> = mutableMapOf()
    private val allPowiats : MutableMap<String, String> = mutableMapOf()
    private val allShuffled : MutableList<String>

    private var pos = 0
    private var wojewodztwo = "małopolskie"

    constructor()
    {
        for (wojewodztwoPair in coordinates)
            for (powiat : String in wojewodztwoPair.value.keys)
                allPowiats[powiat] = wojewodztwoPair.key

        for (wojewodztwo : String in coordinates.keys)
            shuffled[wojewodztwo] = coordinates[wojewodztwo]!!.keys.shuffled().toMutableList()

        allShuffled = allPowiats.keys.shuffled().toMutableList()
    }

    fun getListOfWojewodztw() : List<String> {
        val keys : MutableList<String> = coordinates.keys.toMutableList()
        keys.add("Cala Polska")
        return keys
    }

    fun setWojewodztwo(wojewodztwo : String) {
        this.wojewodztwo = wojewodztwo
        pos = 0
    }

    fun resetWojewodztwo(magicFill: MagicFill) {
        for (powiat: Data in coordinates[wojewodztwo]!!.values)
            for (point: Point in powiat.coordinates)
                magicFill.fill(Point(Global.calc(point.X, powiat.offset.X), Global.calc(point.Y, powiat.offset.Y)), MyColors.grey)

        shuffled[this.wojewodztwo] = coordinates[this.wojewodztwo]!!.keys.shuffled().toMutableList()
    }


    fun getItem() : String{
        if (this.wojewodztwo == "Cala Polska") {
            if (allShuffled.isEmpty())
                return "Finished"

            if (pos >= allShuffled.size)
                pos = 0

            return allShuffled[pos]
        }
        else {
            if (shuffled[this.wojewodztwo]!!.isEmpty())
                return "Finished"

            if (pos >= shuffled[this.wojewodztwo]!!.size)
                pos = 0

            return shuffled[this.wojewodztwo]!![pos]
        }
    }

    fun deleteItem() {
        if (this.wojewodztwo == "Cala Polska") {
            //shuffled[allShuffled[]]!!.remove(shuffled[this.wojewodztwo]!![pos])
        }
        else {
            allShuffled.remove(shuffled[this.wojewodztwo]!![pos])
            shuffled[this.wojewodztwo]!!.remove(shuffled[this.wojewodztwo]!![pos])
        }

    }

    fun skip() {
        pos++

        if (pos >= shuffled[this.wojewodztwo]!!.size)
            pos = 0
    }

    fun isEmpty() : Boolean{
        return shuffled[this.wojewodztwo]!!.isEmpty()
    }

    fun getDpiPoint(scale : Float) : MutableList<Point>{

        val points : MutableList<Point> = mutableListOf()

        for (point : Point in coordinates[wojewodztwo]!![shuffled[this.wojewodztwo]!![pos]]!!.coordinates) {
            val offset: Point = coordinates[wojewodztwo]!![shuffled[this.wojewodztwo]!![pos]]!!.offset
            val x : Int = Global.calc(point.X, offset.X)
            val y : Int = Global.calc(point.Y, offset.Y)
            points.add(Point(x, y))
        }

        return points
    }

    fun testCalibration(bmp : Bitmap, scale : Float) {

        for (wojewodztwo : Map<String, Data> in coordinates.values)
            for (powiat : Data in wojewodztwo.values)
                for (doublePoint : Point in powiat.coordinates) {
                    val x = Global.calc(doublePoint.X, powiat.offset.X)
                    val y = Global.calc(doublePoint.Y, powiat.offset.Y)
                    for (i in 0..5)
                        for (j in 0..5)
                            bmp.setPixel(x + i, y + j, Color.RED)
                }
    }
}