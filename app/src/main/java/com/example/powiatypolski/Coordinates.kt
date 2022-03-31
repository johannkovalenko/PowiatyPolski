package com.example.powiatypolski

import android.graphics.Bitmap
import android.graphics.Color
import com.example.powiatypolski.input.Wojewodztwa

class Coordinates
{
    private val coordinates : Map<String, Map<String, Data>> = mapOf(
            "małopolskie" to Wojewodztwa.Malopolska(),
            "dolnośląskie" to Wojewodztwa.Dolnośląskie(),
            "kujawsko-pomorskie" to Wojewodztwa.Kujawsko_pomorskie(),
            "lubelskie" to Wojewodztwa.Lubelskie(),
            "lubuskie" to Wojewodztwa.Lubuskie(),
            "łódzkie" to Wojewodztwa.Łódzkie(),
            "mazowieckie" to Wojewodztwa.Mazowieckie(),
            "opolskie" to Wojewodztwa.Opolskie(),
            "podkarpackie" to Wojewodztwa.Podkarpackie() ,
            "podlaskie" to Wojewodztwa.Podlaskie(),
            "pomorskie" to Wojewodztwa.Pomorskie(),
            "śląskie" to Wojewodztwa.Śląskie(),
            "świętokrzyskie" to Wojewodztwa.Świętokrzyskie(),
            "warmińsko-mazurskie" to Wojewodztwa.Warmińsko_mazurskie(),
            "wielkopolskie" to Wojewodztwa.Wielkopolskie(),
            "zachodniopomorskie" to Wojewodztwa.Zachodniopomorskie(),
    )
    private val shuffled : MutableMap<String, MutableList<String>> = mutableMapOf()
    private val allPowiats : MutableMap<String, String> = mutableMapOf()
    private val allShuffled : MutableList<String>

    private var mode = false
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
        keys.add("Cała Polska")
        return keys
    }

    fun setMode(mode : Boolean) {
        this.mode = mode
    }

    fun setWojewodztwo(wojewodztwo : String) {
        this.wojewodztwo = wojewodztwo
    }

    fun resetWojewodztwo(magicFill: MagicFill) {
        for (powiat: Data in coordinates[wojewodztwo]!!.values)
            for (point: Point in powiat.coordinates)
                magicFill.fill(Point(Global.calc(point.X, powiat.offset.X), Global.calc(point.Y, powiat.offset.Y)), MyColors.grey)

        shuffled[this.wojewodztwo] = coordinates[this.wojewodztwo]!!.keys.shuffled().toMutableList()
        allShuffled.addAll(coordinates[this.wojewodztwo]!!.keys.toMutableList())
        allShuffled.shuffle()
    }


    fun getItem() : String{

        if (this.wojewodztwo == "Cała Polska") {
            return if (allShuffled.isEmpty())
                "Finished"
            else {
                val powiat = allShuffled.first()
                val wojewodztwo = allPowiats[powiat]!!
                val sign = coordinates[wojewodztwo]!![powiat]!!.code
                if (!mode)
                    "Powiat $powiat"
                else
                    "Powiat $sign"
            }
        } else {
            return if (shuffled[this.wojewodztwo]!!.isEmpty())
                "Finished"
            else {
                val powiat = shuffled[this.wojewodztwo]!!.first()
                val sign = coordinates[this.wojewodztwo]!![powiat]!!.code
                if (!mode)
                    "Powiat $powiat"
                else
                    "Powiat $sign"
            }
        }
    }

    fun deleteItem() {
        if (this.wojewodztwo == "Cała Polska") {
            val currentPowiat : String = allShuffled.first()
            val currentWojewodztwo : String = allPowiats[currentPowiat]!!

            allShuffled.remove(currentPowiat)
            shuffled[currentWojewodztwo]!!.remove(currentPowiat)
        }
        else {
            val currentPowiat : String = shuffled[this.wojewodztwo]!!.first()
            allShuffled.remove(currentPowiat)
            shuffled[this.wojewodztwo]!!.remove(currentPowiat)
        }
    }

    fun skip() {
        if (isEmpty())
            return

        if (this.wojewodztwo == "Cała Polska") {
            val currentPowiat : String = allShuffled.first()
            allShuffled.remove(currentPowiat)
            allShuffled.add(currentPowiat)
        }
        else {
            val currentPowiat : String = shuffled[this.wojewodztwo]!!.first()
            shuffled[this.wojewodztwo]!!.remove(currentPowiat)
            shuffled[this.wojewodztwo]!!.add(currentPowiat)
        }
    }

    fun isEmpty() : Boolean{
        return if (this.wojewodztwo == "Cała Polska")
            allShuffled.isEmpty()
        else
            shuffled[this.wojewodztwo]!!.isEmpty()
    }

    fun getDpiPoint() : MutableList<Point>{

        val points : MutableList<Point> = mutableListOf()

        val currentPowiat = if (this.wojewodztwo == "Cała Polska")
            allShuffled.first()
        else
            shuffled[this.wojewodztwo]!!.first()

        val currentWojewodztwo = if (this.wojewodztwo == "Cała Polska")
            allPowiats[currentPowiat]!!
        else
            this.wojewodztwo

        for (point : Point in coordinates[currentWojewodztwo]!![currentPowiat]!!.coordinates) {
            val offset: Point = coordinates[currentWojewodztwo]!![currentPowiat]!!.offset
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