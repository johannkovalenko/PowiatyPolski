package com.example.powiatypolski

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private var coordinates : Coordinates? = null
    private var magicFill : MagicFill? = null
    private var check : Check? = null

    var selectedWojewodztwo = ""
    var allowSkip = true
    var scale : Float = 0f

//    var xDown = 0f
//    var yDown = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scale = resources.displayMetrics.density

        val mapPoland : Bitmap = loadImage()

        magicFill = MagicFill(mapPoland)
        coordinates = Coordinates()

        getItem()

        val image : ImageView = findViewById(R.id.mapPoland)
        //coordinates?.testCalibration(mapPoland, scale)

        //image.x = -500f
        //image.y = -500f

        image.setImageBitmap(mapPoland)

        val reset = Reset(this, coordinates!!, magicFill!!)
        check = Check(this, coordinates!!, magicFill!!, image, mapPoland)

        fillDropdown(coordinates?.getListOfWojewodztw()!!)
        supportBar()
        //licencePlates()
        allowSkip()
        moveToEnd()
        hideCorrectlySolved()

        image.setOnTouchListener { _, event ->

            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    findViewById<NavigationView>(R.id.leftMenu).visibility = View.GONE
//                    xDown = event.x
//                    yDown = event.y
                    true
                }
//                MotionEvent.ACTION_MOVE -> {
//                    val moveX: Float = event.x
//                    val moveY: Float = event.y
//                    val distanceX: Float = moveX - xDown
//                    val distanceY: Float = moveY - yDown
//                    image.x = image.x + distanceX
//                    image.y = image.y + distanceY
//                    Log.i("AAA", "IN")
//                    true
//                }

                MotionEvent.ACTION_UP -> {


                    if (reset.Wojedwodztwo(selectedWojewodztwo))
                        return@setOnTouchListener true
                    if (reset.Polska(selectedWojewodztwo, mapPoland))
                        return@setOnTouchListener true

                    check?.run(event)
                    true
                }


                else -> true
            }

           // true
        }
    }

    private fun loadImage() : Bitmap {

        val image : Int = when (selectedWojewodztwo) {
            "małopolskie" -> R.drawable.malopolskie
            else -> R.drawable.powiatywzor
        }

        return BitmapFactory.decodeResource(resources, image).copy(
                Bitmap.Config.ARGB_8888,
                true
        )
    }

    private fun getItem() {
        supportActionBar?.title = coordinates?.getItem()
    }

    private fun supportBar() {
        val gd = GradientDrawable()
        gd.setColor(MyColors.purple)
        gd.cornerRadius = 20.convF()
        gd.setStroke(2.conv(), Color.BLACK)
        supportActionBar?.setBackgroundDrawable(gd)

    }

    private fun Int.convF() : Float {
        return (this.toFloat() * scale + 0.5f)
    }
    private fun Int.conv() : Int {
        return (this.toFloat() * scale + 0.5f).toInt()
    }

    private fun fillDropdown(keys: List<String>) {
        val spinner: Spinner = findViewById(R.id.spinner)

        spinner.bringToFront()
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, keys)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                selectedWojewodztwo = spinner.selectedItem as String
                coordinates!!.setWojewodztwo(selectedWojewodztwo)

                if (check!!.isReset())
                    magicFill?.recolor(MyColors.grey)
                getItem()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

//    private fun licencePlates() {
//
//        findViewById<Switch>(R.id.toggle).setOnCheckedChangeListener { _, isChecked ->
//            coordinates?.setMode(isChecked)
//            getItem()
//        }
//    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            when (view.getId()) {
                R.id.powiatname ->
                    if (view.isChecked)
                        coordinates?.setMode(CheckMode.NAME)
                R.id.powiatlicenseplate ->
                    if (view.isChecked)
                        coordinates?.setMode(CheckMode.LICENSEPLATE)
                R.id.powiatcapital->
                    if (view.isChecked)
                        coordinates?.setMode(CheckMode.CAPITAL)
            }

            getItem()
        }
    }

    private fun hideCorrectlySolved() {
        findViewById<Switch>(R.id.hideCorrectlySolved).setOnCheckedChangeListener { _, isChecked ->
            check?.setHideCorrectlySolved(isChecked)
            //findViewById<Switch>(R.id.allowSkip).isChecked = !isChecked
        }
    }

    private fun allowSkip() {
        findViewById<Switch>(R.id.allowSkip).setOnCheckedChangeListener { _, isChecked ->
            allowSkip = isChecked
            invalidateOptionsMenu()
        }
    }

    private fun moveToEnd() {
        findViewById<Switch>(R.id.moveIfFalse).setOnCheckedChangeListener { _, isChecked ->
            check?.setMoveToEnd(isChecked)
            invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        menu?.findItem(R.id.skip)?.isVisible = allowSkip


        //val itemswitch = menu!!.findItem(R.id.switcher)
        //itemswitch.setActionView(R.layout.use_switch)


//        val item = menu!!.findItem(R.id.spinner)
//        val spinner = item.actionView as Spinner
//
//        val adapter = ArrayAdapter.createFromResource(
//            this,
//            R.array.spinner_list_item_array, android.R.layout.simple_spinner_item
//        )
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//
//        spinner.adapter = adapter
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.skip -> {
            coordinates?.skip()
            if (check!!.isReset())
                magicFill?.recolor(MyColors.grey)
            getItem()
            true
        }

        R.id.showMenu -> {
            val menu = findViewById<NavigationView>(R.id.leftMenu)

            if (menu.visibility == View.VISIBLE)
                menu.visibility = View.GONE
            else menu.visibility = View.VISIBLE
            true
        }

        else -> super.onOptionsItemSelected(item)
    }

}