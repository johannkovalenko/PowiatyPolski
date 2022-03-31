package com.example.powiatypolski

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var coordinates : Coordinates? = null
    private var magicFill : MagicFill? = null
    private var spinner: Spinner? = null
    var selectedWojewodztwo = ""
    var check : Check? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val scale: Float = resources.displayMetrics.density

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

        spinner = findViewById(R.id.spinner)

        spinner?.bringToFront()

        fillDropdown(coordinates?.getListOfWojewodztw()!!)
        spinnerSetListener()
        toggleButton()

        image.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_UP) {


            }
            when (event.actionMasked) {
                MotionEvent.ACTION_UP -> {
                    if (reset.Wojedwodztwo(selectedWojewodztwo))
                        return@setOnTouchListener true
                    if (reset.Polska(selectedWojewodztwo, mapPoland))
                        return@setOnTouchListener true

                    check?.run(event)
                }

                MotionEvent.ACTION_DOWN -> {
                    xDown = event.x
                    yDown = event.y
                }
                MotionEvent.ACTION_MOVE -> {
                    val moveX: Float
                    val moveY: Float
                    moveX = event.x
                    moveY = event.y
                    val distanceX: Float = moveX - xDown
                    val distanceY: Float = moveY - yDown
                    image.setX(image.getX() + distanceX)
                    image.setY(image.getY() + distanceY)
                }
            }

            true
        }
    }

    private fun loadImage() : Bitmap {
        return BitmapFactory.decodeResource(resources, R.drawable.powiatywzor).copy(
                Bitmap.Config.ARGB_8888,
                true
        )
    }

    private fun getItem() {
        supportActionBar!!.title = coordinates?.getItem()
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

                if (check!!.isReset())
                    magicFill?.recolor(MyColors.grey)
                getItem()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun toggleButton() {

        val toggle: Switch = findViewById(R.id.toggle)
        toggle.bringToFront()
        toggle.setOnCheckedChangeListener { _, isChecked ->
            coordinates?.setMode(isChecked)
            getItem()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

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

        else -> super.onOptionsItemSelected(item)
    }

}