package com.example.powiatypolski

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView

class Check (private val mainActivity: MainActivity,
             private val coordinates: Coordinates,
             private val magicFill: MagicFill,
             private val image : ImageView,
             private val mapPoland : Bitmap){

    private var toBeResetAtNextClick = false
    private var hideCorrectlySolved = false
    private var moveToEnd = false

    fun isReset() : Boolean {
        return toBeResetAtNextClick
    }

    fun setHideCorrectlySolved(option : Boolean) {
        hideCorrectlySolved = option
    }

    fun setMoveToEnd(option : Boolean) {
        moveToEnd = option
    }

    fun run(event : MotionEvent) {

        val dpiPoints : List<Point> = coordinates!!.getDpiPoint()
        val startPoint = Point(Global.calc(event.x), Global.calc(event.y))

        if(toBeResetAtNextClick) {
            magicFill?.recolor(MyColors.grey)
            toBeResetAtNextClick = false
        }

        if (magicFill!!.isContinue(startPoint)) {
            val result: Boolean = magicFill!!.check(startPoint, dpiPoints, Color.YELLOW)

            magicFill!!.recolor(MyColors.grey)

            if (result) {
                magicFill?.reset()
                for (dpiPoint: Point in dpiPoints)
                    magicFill?.fill(dpiPoint, MyColors.green)

                if (hideCorrectlySolved)
                    toBeResetAtNextClick = true

                coordinates!!.deleteItem()
                mainActivity.supportActionBar!!.title = coordinates?.getItem()
            }
            else {
                magicFill?.reset()
                magicFill?.fill(startPoint, Color.RED)
                coordinates.incrementWrong()
                toBeResetAtNextClick = true

                if (moveToEnd) {
                    coordinates?.skip()
                    mainActivity.supportActionBar!!.title = coordinates?.getItem()
                }
            }

            image.setImageBitmap(mapPoland)
        }
    }
}