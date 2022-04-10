package com.example.powiatypolski

import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.res.ResourcesCompat


class MyButton(
        private val scale : Float,
        private val mainActivity: MainActivity,
        text1 : String,
        private val width1 : Int,
        private val height1 : Int,
        backgroundColor1: Int,
        private val borderColor1 : Int,
        private val borderWidth1 : Int,
        private val borderRadius1 : Int,
        typeFace1 : Int,
        gravity1 : Int,
        paddings1 : Array<Int>,
        private val margins1 : Array<Int>
) : AppCompatButton(mainActivity) {

    var marked = false

    private val lp = customizeLayoutParams()
    private val gd = customizeButton(backgroundColor1)
    private val tf = customizeFont()

    init {
        layoutParams = lp

        transformationMethod = null
        background = gd
        typeface = tf
        setTextAndFontSize(text1)
        gravity = gravity1
        setPadding(paddings1[0].conv(), paddings1[1].conv(), paddings1[2].conv(), paddings1[3].conv())

                super.setTypeface(super.getTypeface(), typeFace1)
    }

    fun recolor(color : Int) {
        val gd = GradientDrawable()
        gd.setColor(color)
        gd.cornerRadius = borderRadius1.convF()
        gd.setStroke(borderWidth1.conv(), borderColor1)
        background = gd
    }

    private fun Int.conv() : Int {
        return (this.toFloat() * scale + 0.5f).toInt()
    }

    private fun Float.conv() : Int {
        return (this * scale + 0.5f).toInt()
    }

    private fun Int.convF() : Float {
        return (this.toFloat() * scale + 0.5f)
    }

    private fun Float.convF() : Float {
        return (this * scale + 0.5f)
    }

    private fun convFloat(input : Float) : Float {
        return (input * scale + 0.5f)
    }

    fun setTextAndFontSize(textIn : String){
        text = textIn

        textSize = if(isUnicodeBlock(textIn, Character.UnicodeBlock.HEBREW))
            17f
        else if (isUnicodeBlock(textIn, Character.UnicodeBlock.ARABIC))
            20f
        else
            15f
    }

    private fun isUnicodeBlock(text: String, unicodeBlock : Character.UnicodeBlock): Boolean {
        for (char : Char in text) {
            if (Character.UnicodeBlock.of(char) === unicodeBlock) {
                return true
            }
        }
        return false
    }
    fun setColor(backgroundColor : Int) {
        background = customizeButton(backgroundColor)
    }

    private fun customizeButton(backgroundColor : Int) : GradientDrawable
    {
        val gd = GradientDrawable()
        gd.setColor(backgroundColor)

        gd.cornerRadius = borderRadius1.convF()

        gd.setStroke(borderWidth1.conv(), borderColor1)

        return gd
    }

    private fun customizeLayoutParams() : LinearLayout.LayoutParams {

        val lp : LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        lp.setMargins(margins1[0].conv(), margins1[1].conv(), margins1[2].conv(), margins1[3].conv())
        lp.width = width1.conv()
        lp.height = height1.conv()

        return lp
    }

    private fun customizeFont() : Typeface? {
        return ResourcesCompat.getFont(mainActivity, R.font.bahnschrift)
    }
}