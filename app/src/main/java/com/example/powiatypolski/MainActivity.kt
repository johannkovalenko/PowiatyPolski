package com.example.powiatypolski

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var coordinates : Coordinates? = null
    private var toBeResetAtNextClick = false
    private var magicFill : MagicFill? = null

    private var logCounter = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scale: Float = resources.displayMetrics.density

        (findViewById<Button>(R.id.startButton)).transformationMethod = null

        val mapPoland : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.powiatywzor).copy( Bitmap.Config.ARGB_8888 , true)

        magicFill = MagicFill(mapPoland)
        coordinates = Coordinates()

        getItem()

        val image : ImageView = findViewById(R.id.mapPoland)
        //zcoordinates?.testCalibration(mapPoland, scale)

        image.setImageBitmap(mapPoland)

        image.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val dpiPoints : List<Point> = coordinates!!.getDpiPoint(scale)
                val startPoint = Point(event.x.toInt() / Global.granularity * Global.granularity, event.y.toInt() / Global.granularity * Global.granularity)

                if (magicFill!!.isContinue(startPoint)) {
                    if(toBeResetAtNextClick) {

                        magicFill?.recolor(MyColors.grey)
                        toBeResetAtNextClick = false
                    }
                    val result: Boolean = magicFill!!.fill(startPoint, dpiPoints, Color.YELLOW)

                    if (result) {
                        for (dpiPoint: Point in dpiPoints)
                            magicFill?.fill(dpiPoint, listOf(Point(-1, -1)), MyColors.green)

                        coordinates?.deleteItem()
                        getItem()
                    }
                    else {
                        magicFill?.fill(startPoint, listOf(Point(-1, -1)), Color.RED)
                        toBeResetAtNextClick = true
                    }

                    image.setImageBitmap(mapPoland)
                }
            }

            true
        }
    }


    private fun getItem() {
        val button : Button = findViewById(R.id.startButton)
        val item = coordinates?.getItem()
        if (item!!.success)
            button.text = item?.powiat
        else
            button.text = "Finished"
    }

    fun skip(view : View) {
        coordinates?.skip()
        if (toBeResetAtNextClick)
            magicFill?.recolor(MyColors.grey)
        getItem()
    }
}