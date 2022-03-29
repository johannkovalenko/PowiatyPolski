package com.example.powiatypolski

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private var coordinates : Coordinates? = null
    private var toBeResetAtNextClick = false
    private var magicFill : MagicFill? = null
    private var spinner: Spinner? = null
    private var selectedWojewodztwo = ""
    //private var isFinished = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scale: Float = resources.displayMetrics.density

        (findViewById<Button>(R.id.startButton)).transformationMethod = null

        val mapPoland : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.powiatywzor).copy( Bitmap.Config.ARGB_8888 , true)

        magicFill = MagicFill(mapPoland)
        coordinates = Coordinates()

        spinner = findViewById(R.id.spinner)

        spinner?.bringToFront()

        fillDropdown(coordinates?.getListOfWojewodztw()!!)
        spinnerSetListener()

        getItem()

        val image : ImageView = findViewById(R.id.mapPoland)
        //coordinates?.testCalibration(mapPoland, scale)

        image.setImageBitmap(mapPoland)

        image.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {

                if (coordinates!!.isEmpty()) {
                    val snack = Snackbar.make(findViewById(R.id.startButton),"Województwo $selectedWojewodztwo jest rozwiązane.",Snackbar.LENGTH_LONG)
                    snack.setAction("Zresetować je?") {
                        coordinates!!.resetWojewodztwo(magicFill!!)
                        getItem()
                    }
                    snack.show()
                    return@setOnTouchListener true
                }

                val dpiPoints : List<Point> = coordinates!!.getDpiPoint(scale)
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
                        getItem()
                    }
                    else {
                        magicFill?.fill(startPoint, Color.RED)
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
        button.text = coordinates?.getItem()
    }

    fun skip(view : View) {
        coordinates?.skip()
        if (toBeResetAtNextClick)
            magicFill?.recolor(MyColors.grey)
        getItem()
    }

    private fun fillDropdown(keys: List<String>) {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, keys)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = arrayAdapter
    }

    private fun spinnerSetListener() {
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                selectedWojewodztwo = spinner?.selectedItem as String
                coordinates!!.setWojewodztwo(selectedWojewodztwo)

                if (toBeResetAtNextClick)
                    magicFill?.recolor(MyColors.grey)
                getItem()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }
}