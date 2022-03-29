package com.example.powiatypolski

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log

class MagicFill(private var bmp: Bitmap)
{
    private val canvas = IntArray(bmp.width * bmp.height)

    init {
        bmp.getPixels(canvas, 0, bmp.width, 0, 0,
                bmp.width, bmp.height)
    }

    private val points : MutableList<Point> = mutableListOf()
    private val directions : Array<Array<Int>> = arrayOf(
            arrayOf(0, Global.granularity),
            arrayOf(0, -Global.granularity),
            arrayOf(Global.granularity, 0),
            arrayOf(-Global.granularity, 0)
    )

    fun isContinue(startPoint: Point) : Boolean {
        return when (getColor(startPoint)) {
            Color.YELLOW -> true
            MyColors.grey -> true
            Color.RED -> true
            else -> false
        }
    }

    fun recolor(color: Int) {
        for (point : Point in points)
            colorRange(point, color)
        points.clear()

        setPixels()
    }

    private fun colorRange(point: Point, color: Int) {
//        for (i in 0 until Global.granularity - 1)
//            for (j in 0 until Global.granularity - 1)
        val index : Int = point.Y * bmp.width + point.X
        if (index < canvas.size)
            canvas[index] = color
    }

    private fun setPixels() {
        bmp.setPixels(canvas, 0, bmp.width, 0, 0, bmp.width, bmp.height)
    }

    private fun getColor(point : Point) : Int {
        val index : Int = point.Y * bmp.width + point.X
        return if (index >= canvas.size)
            Color.RED
        else
            canvas[index]
    }

    fun fill(startPoint: Point, color: Int){

        points.clear()
        points.add(startPoint)

        var startIndex = 0

        val startColor : Int = getColor(startPoint)

        while (true) {
            val iterations : Int = points.size
            for (it in startIndex until iterations) {
                val point : Point = points[it]

                for(direction : Array<Int> in directions) {
                    val neighbor = Point(point.X + direction[0], point.Y + direction[1])

                    if (neighbor.X < 0 || neighbor.Y  < 0)
                        break

                    val neighborColor : Int = getColor(neighbor)

                    if (neighborColor == startColor) { // || neighborColor == MyColors.grey) {
                        colorRange(neighbor, color)
                        points.add(neighbor)
                    }
                }
            }
            if (points.size == iterations)
                break

            startIndex = iterations - 1
        }

        if (color == MyColors.green)
            points.clear()

        setPixels()
    }

    fun check(startPoint: Point, correctPoints: List<Point>, color: Int) : Boolean{

        points.clear()
        points.add(startPoint)

        var result = false

        var startIndex = 0

        val startColor : Int = getColor(startPoint)

        while (true) {
            val iterations : Int = points.size
            for (it in startIndex until iterations) {
                val point : Point = points[it]

                for (correctPoint : Point in correctPoints)
                    if (point.X == correctPoint.X && point.Y == correctPoint.Y)
                        if (color == Color.YELLOW)
                            return true
                        else
                            result = true

                for(direction : Array<Int> in directions) {
                    val neighbor = Point(point.X + direction[0], point.Y + direction[1])

                    if (neighbor.X < 0 || neighbor.Y  < 0)
                        break

                    val neighborColor : Int = getColor(neighbor)

                    if (neighborColor == startColor) {
                        colorRange(neighbor, color)
                        points.add(neighbor)
                    }
                }
            }
            if (points.size == iterations)
                break

            startIndex = iterations - 1
        }

        if (color == MyColors.green)
            points.clear()

        setPixels()
        return result
    }
}