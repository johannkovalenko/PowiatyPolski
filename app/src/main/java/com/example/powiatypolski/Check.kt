package com.example.powiatypolski

import android.graphics.Bitmap
import android.graphics.Color
import android.view.MotionEvent
import android.widget.ImageView

class Check (private val mainActivity: MainActivity,
             private val coordinates: Coordinates,
             private val magicFill: MagicFill,
             private val image : ImageView,
             private val mapPoland : Bitmap){

    private var toBeResetAtNextClick = false

    fun isReset() : Boolean {
        return toBeResetAtNextClick
    }
    fun run(event : MotionEvent) {
        val dpiPoints : List<Point> = coordinates!!.getDpiPoint()
        val startPoint = Point(Global.calc(event.x), Global.calc(event.y))

        if (magicFill!!.isContinue(startPoint)) {
            if(toBeResetAtNextClick) {
                magicFill?.recolor(MyColors.grey)
                toBeResetAtNextClick = false
            }
            val result: Boolean = magicFill!!.check(startPoint, dpiPoints, Color.YELLOW)

            magicFill!!.recolor(MyColors.grey)

            if (result) {
                for (dpiPoint: Point in dpiPoints)
                    magicFill?.fill(dpiPoint, MyColors.green)

                coordinates!!.deleteItem()
                mainActivity.supportActionBar!!.title = coordinates?.getItem()
            }
            else {
                magicFill?.fill(startPoint, Color.RED)
                toBeResetAtNextClick = true
            }

            image.setImageBitmap(mapPoland)
        }
    }
}