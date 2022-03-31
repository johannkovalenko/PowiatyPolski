package com.example.powiatypolski

import android.graphics.Bitmap
import com.google.android.material.snackbar.Snackbar

class Reset (private val mainActivity: MainActivity, private val coordinates: Coordinates, private val magicFill: MagicFill){
    fun Wojedwodztwo(selectedWojewodztwo : String) : Boolean{

        return if (selectedWojewodztwo != "Cała Polska" && coordinates!!.isEmpty()) {
            val snack = Snackbar.make(
                    mainActivity.findViewById(R.id.spinner),
                    "Województwo $selectedWojewodztwo jest rozwiązane.",
                    Snackbar.LENGTH_LONG
            )
            snack.setAction("Zresetować je?") {
                coordinates.resetWojewodztwo(magicFill)
                mainActivity.supportActionBar!!.title = coordinates?.getItem()
            }
            snack.show()
            true
        }
        else false
    }

    fun Polska(selectedWojewodztwo : String, mapPoland : Bitmap) : Boolean{

        return if(selectedWojewodztwo == "Cała Polska" && coordinates!!.isEmpty()) {
            val snack = Snackbar.make(
                    mainActivity.findViewById(R.id.spinner),
                    "Cała Polska jest rozwiązana.",
                    Snackbar.LENGTH_LONG
            )
            snack.setAction("Zresetować mapę?") {
//                mapPoland = loadImage()
//                image.setImageBitmap(mapPoland)
//                magicFill = MagicFill(mapPoland)
            }
            snack.show()
            true
        }
        else false
    }
}