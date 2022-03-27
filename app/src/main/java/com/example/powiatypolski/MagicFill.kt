package com.example.powiatypolski

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log

class MagicFill(private var bmp : Bitmap)
{
    private val points : MutableList<Point> = mutableListOf()
    private val directions : Array<Array<Int>> = arrayOf(
            arrayOf(0, Global.granularity),
            arrayOf(0, -Global.granularity),
            arrayOf(Global.granularity, 0),
            arrayOf(-Global.granularity, 0)
    )

    fun isContinue(startPoint: Point) : Boolean {

        when (bmp.getPixel(startPoint.X, startPoint.Y)) {
            Color.YELLOW -> return true
            MyColors.grey -> return true
            Color.RED -> return true
            else -> return false
        }
    }

    fun recolor(color : Int) {
        for (point : Point in points)
            bmp.setPixel(point.X, point.Y, color)
        points.clear()
    }

    fun fill(startPoint: Point, correctPoints : List<Point>, color : Int) : Boolean{

        points.clear()
        points.add(startPoint)

        var result = false

        var startIndex = 0

        val startColor : Int = bmp.getPixel(startPoint.X, startPoint.Y)

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

                    val neighborColor : Int = bmp.getPixel(neighbor.X, neighbor.Y)

                    if (neighborColor == startColor || neighborColor == MyColors.grey) {
                        bmp.setPixel(neighbor.X, neighbor.Y, color)
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

        return result
    }
}