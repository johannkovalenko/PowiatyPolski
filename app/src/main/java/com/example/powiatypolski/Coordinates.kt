package com.example.powiatypolski

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log

class Coordinates
{
    val coordinates : Map<String, Map<String, Data>>
    private val shuffled : MutableList<String>
    private var pos = 0
    private var wojewodztwo = "małopolskie"

    constructor()
    {
        coordinates = mapOf(
                "małopolskie" to Malopolska(),
                "podkarpackie" to Podkarpackie() ,
                "świętokrzyskie" to Świętokrzyskie(),
                "śląskie" to Śląskie(),
                "opolskie" to Opolskie(),
                "dolnośląskie" to Dolnośląskie(),
                "lubuskie" to Lubuskie(),
                "zachodniopomorskie" to Zachodniopomorskie(),
                "pomorskie" to Pomorskie(),
                "warmińsko-mazurskie" to Warmińsko_mazurskie(),
                "podlaskie" to Podlaskie(),
                "lubelskie" to Lubelskie(),
                "kujawsko-pomorskie" to Kujawsko_pomorskie(),
                "łódzkie" to Łódzkie(),
                "wielkopolskie" to Wielkopolskie(),
                "mazowieckie" to Mazowieckie(),
        )


        shuffled = coordinates[wojewodztwo]!!.keys.toMutableList()
        shuffled.shuffle()
    }

    fun getItem() : Item{
        
        if (shuffled.isEmpty())
            return Item("", Point(0, 0), false)

        if (pos >= shuffled.size)
            pos = 0

        val powiat = shuffled[pos]
        
        return Item("$powiat\n${coordinates[wojewodztwo]!![powiat]!!.wojewodztwo}", coordinates[wojewodztwo]!![powiat]!!.coordinates[0], true)
    }

    fun deleteItem() : Boolean {
        shuffled.remove(shuffled[pos])

        return shuffled.isEmpty()
    }

    fun skip() {
        pos++

        if (pos >= shuffled.size)
            pos = 0
    }

    fun getDpiPoint(scale : Float) : MutableList<Point>{
        val points : MutableList<Point> = mutableListOf()

        for (point : Point in coordinates[wojewodztwo]!![shuffled[pos]]!!.coordinates) {
            val offset: Point = coordinates[wojewodztwo]!![shuffled[pos]]!!.offset
            val x : Int = ((point.X / scale).toInt() + offset.X) / Global.granularity * Global.granularity
            val y : Int = ((point.Y / scale).toInt() + offset.Y) / Global.granularity * Global.granularity
            points.add(Point(x, y))
        }

        return points
    }

    fun testCalibration(bmp : Bitmap, scale : Float) {

        for (data : Data in coordinates[wojewodztwo]!!.values) {

            val doublePoints : Array<Point> = data.coordinates

            for (doublePoint : Point in doublePoints) {
                val x = (doublePoint.X / scale).toInt() + data.offset.X
                val y = (doublePoint.Y / scale).toInt() + data.offset.Y
                for (i in 0..5)
                    for (j in 0..5)
                        bmp.setPixel(x + i, y + j, Color.RED)
            }
        }
    }

    private fun Malopolska() : Map<String, Data>
    { return mapOf(
            "Tarnów" to Data("małopolskie", arrayOf(Point(3200, 3680)), Point(35, 35)),
            "tatrzański" to Data("małopolskie", arrayOf(Point(2750, 4230)), Point(35, 35)),
            "proszowicki" to Data("małopolskie", arrayOf(Point(2890, 3520)), Point(35, 35)),
            "krakowski" to Data("małopolskie", arrayOf(Point(2650, 3560), Point(2880, 3620)), Point(35, 35)),
            "wielicki" to Data("małopolskie", arrayOf(Point(2830, 3710)), Point(35, 35)),
            "miechowski" to Data("małopolskie", arrayOf(Point(2780, 3390)), Point(35, 35)),
            "oświęcimski" to Data("małopolskie", arrayOf(Point(2400, 3700)), Point(35, 35)),
            "myślenicki" to Data("małopolskie", arrayOf(Point(2740, 3840)), Point(35, 35)),
            "tarnowski" to Data("małopolskie", arrayOf(Point(3210, 3760)), Point(35, 35)),
            "Kraków" to Data("małopolskie", arrayOf(Point(2740, 3650)), Point(35, 35)),
            "wadowicki" to Data("małopolskie", arrayOf(Point(2530, 3770)), Point(35, 35)),
            "chrzanowski" to Data("małopolskie", arrayOf(Point(2480, 3600)), Point(35, 35)),
            "Nowy Sącz" to Data("małopolskie", arrayOf(Point(3080, 3990)), Point(35, 35)),
            "bocheński" to Data("małopolskie", arrayOf(Point(2950, 3740)), Point(35, 35)),
            "olkuski" to Data("małopolskie", arrayOf(Point(2570, 3420)), Point(35, 35)),
            "nowosądecki" to Data("małopolskie", arrayOf(Point(3150, 4010)), Point(35, 35)),
            "dąbrowski" to Data("małopolskie", arrayOf(Point(3210, 3515)), Point(35, 35)),
            "suski" to Data("małopolskie", arrayOf(Point(2580, 3920)), Point(35, 35)),
            "limanowski" to Data("małopolskie", arrayOf(Point(2880, 3920)), Point(35, 35)),
            "gorlicki" to Data("małopolskie", arrayOf(Point(3300, 4000)), Point(35, 35)),
            "brzeski" to Data("małopolskie", arrayOf(Point(3060, 3710)), Point(35, 35)),
            "nowotarski" to Data("małopolskie", arrayOf(Point(2710, 4080)), Point(35, 35)),
    )}

    private fun Świętokrzyskie() : Map<String, Data>
    { return mapOf(
            "buski" to Data("świętokrzyskie", arrayOf(Point(3140, 3340)), Point(35, 35)),
            "jędrzejowski" to Data("świętokrzyskie", arrayOf(Point(2900, 3200)), Point(35, 35)),
            "kazimierski" to Data("świętokrzyskie", arrayOf(Point(3000, 3500)), Point(35, 35)),
            "Kielce" to Data("świętokrzyskie", arrayOf(Point(3040, 3040)), Point(35, 35)),
            "kielecki" to Data("świętokrzyskie", arrayOf(Point(3120, 3090)), Point(35, 35)),
            "konecki" to Data("świętokrzyskie", arrayOf(Point(2920, 2830)), Point(35, 35)),
            "opatowski" to Data("świętokrzyskie", arrayOf(Point(3400, 3100)), Point(35, 35)),
            "ostrowiecki" to Data("świętokrzyskie", arrayOf(Point(3400, 2970)), Point(35, 35)),
            "pińczowski" to Data("świętokrzyskie", arrayOf(Point(3000, 3300)), Point(35, 35)),
            "sandomierski" to Data("świętokrzyskie", arrayOf(Point(3490, 3200)), Point(35, 35)),
            "skarżyski" to Data("świętokrzyskie", arrayOf(Point(3120, 2870)), Point(35, 35)),
            "starachowicki" to Data("świętokrzyskie", arrayOf(Point(3260, 2900)), Point(35, 35)),
            "staszowski" to Data("świętokrzyskie", arrayOf(Point(3340, 3300)), Point(35, 35)),
            "włoszczowski" to Data("świętokrzyskie", arrayOf(Point(2700, 3050)), Point(35, 35)),
    )}

    private fun Podkarpackie() : Map<String, Data>
    { return mapOf(
            "bieszczadzki" to Data("podkarpackie", arrayOf(Point(3980, 4070)), Point(40, 45)),
            "brzozowski" to Data("podkarpackie", arrayOf(Point(3720, 3930)), Point(40, 45)),
            "dębicki" to Data("podkarpackie", arrayOf(Point(3400, 3660)), Point(40, 45)),
            "jarosławski" to Data("podkarpackie", arrayOf(Point(4000, 3660)), Point(40, 45)),
            "jasielski" to Data("podkarpackie", arrayOf(Point(3450, 3960)), Point(40, 45)),
            "kolbuszowski" to Data("podkarpackie", arrayOf(Point(3570, 3500)), Point(40, 45)),
            "Krosno" to Data("podkarpackie", arrayOf(Point(3570, 3930)), Point(40, 45)),
            "krośnieński" to Data("podkarpackie", arrayOf(Point(3570, 4070)), Point(40, 45)),
            "leski" to Data("podkarpackie", arrayOf(Point(3860, 4070)), Point(40, 45)),
            "leżajski" to Data("podkarpackie", arrayOf(Point(3890, 3500)), Point(40, 45)),
            "lubaczowski" to Data("podkarpackie", arrayOf(Point(4200, 3500)), Point(40, 45)),
            "łańcucki" to Data("podkarpackie", arrayOf(Point(3800, 3660)), Point(40, 45)),
            "mielecki" to Data("podkarpackie", arrayOf(Point(3400, 3500)), Point(40, 45)),
            "niżański" to Data("podkarpackie", arrayOf(Point(3830, 3250)), Point(40, 45)),
            "przemyski" to Data("podkarpackie", arrayOf(Point(3900, 3930)), Point(40, 45)),
            "Przemyśl" to Data("podkarpackie", arrayOf(Point(4040, 3860)), Point(40, 45)),
            "przeworski" to Data("podkarpackie", arrayOf(Point(3900, 3700)), Point(40, 45)),
            "ropczycko-sędziszowski" to Data("podkarpackie", arrayOf(Point(3570, 3660)), Point(40, 45)),
            "rzeszowski" to Data("podkarpackie", arrayOf(Point(3690, 3600)), Point(40, 45)),
            "Rzeszów" to Data("podkarpackie", arrayOf(Point(3690, 3690)), Point(40, 45)),
            "sanocki" to Data("podkarpackie", arrayOf(Point(3700, 4070)), Point(40, 45)),
            "stalowowolski" to Data("podkarpackie", arrayOf(Point(3700, 3250)), Point(40, 45)),
            "strzyżowski" to Data("podkarpackie", arrayOf(Point(3570, 3780)), Point(40, 45)),
            "Tarnobrzeg" to Data("podkarpackie", arrayOf(Point(3550, 3250)), Point(40, 45)),
            "tarnobrzeski" to Data("podkarpackie", arrayOf(Point(3580, 3300)), Point(40, 45)),
    )}

    private fun Śląskie() : Map<String, Data>
    { return mapOf(
            "będziński" to Data("śląskie", arrayOf(Point(2350, 3370), Point(2450, 3470)), Point(28, 40)),
            "bielski" to Data("śląskie", arrayOf(Point(2300, 3760), Point(2300, 3900)), Point(28, 40)),
            "Bielsko-Biała" to Data("śląskie", arrayOf(Point(2300, 3830)), Point(28, 40)),
            "bieruńsko-lędziński" to Data("śląskie", arrayOf(Point(2350, 3600)), Point(28, 40)),
            "Bytom" to Data("śląskie", arrayOf(Point(2220, 3410)), Point(28, 40)),
            "Chorzów" to Data("śląskie", arrayOf(Point(2270, 3460)), Point(28, 40)),
            "cieszyński" to Data("śląskie", arrayOf(Point(2160, 3850)), Point(28, 40)),
            "Częstochowa" to Data("śląskie", arrayOf(Point(2350, 3080)), Point(28, 40)),
            "częstochowski" to Data("śląskie", arrayOf(Point(2500, 3080)), Point(28, 40)),
            "Dąbrowa Górnicza" to Data("śląskie", arrayOf(Point(2400, 3420)), Point(28, 40)),
            "Gliwice" to Data("śląskie", arrayOf(Point(2120, 3450)), Point(28, 40)),
            "gliwicki" to Data("śląskie", arrayOf(Point(2040, 3450)), Point(28, 40)),
            "Jastrzębie-Zdrój" to Data("śląskie", arrayOf(Point(2100, 3730)), Point(28, 40)),
            "Jaworzno" to Data("śląskie", arrayOf(Point(2420, 3540)), Point(28, 40)),
            "Katowice" to Data("śląskie", arrayOf(Point(2300, 3520)), Point(28, 40)),
            "kłobucki" to Data("śląskie", arrayOf(Point(2200, 3000)), Point(28, 40)),
            "lubliniecki" to Data("śląskie", arrayOf(Point(2150, 3150)), Point(28, 40)),
            "mikołowski" to Data("śląskie", arrayOf(Point(2200, 3600)), Point(28, 40)),
            "Mysłowice" to Data("śląskie", arrayOf(Point(2340, 3540)), Point(28, 40)),
            "myszkowski" to Data("śląskie", arrayOf(Point(2400, 3230)), Point(28, 40)),
            "Piekary Śląskie" to Data("śląskie", arrayOf(Point(2260, 3390)), Point(28, 40)),
            "pszczyński" to Data("śląskie", arrayOf(Point(2240, 3700)), Point(28, 40)),
            "raciborski" to Data("śląskie", arrayOf(Point(1900, 3600)), Point(28, 40)),
            "Ruda Śląska" to Data("śląskie", arrayOf(Point(2220, 3500)), Point(28, 40)),
            "rybnicki" to Data("śląskie", arrayOf(Point(2000, 3610), Point(2140, 3590), Point(2080, 3670)), Point(28, 40)),
            "Rybnik" to Data("śląskie", arrayOf(Point(2070, 3610)), Point(28, 40)),
            "Siemianowice Śląskie" to Data("śląskie", arrayOf(Point(2300, 3450)), Point(28, 40)),
            "Sosnowiec" to Data("śląskie", arrayOf(Point(2370, 3480)), Point(28, 40)),
            "Świętochłowice" to Data("śląskie", arrayOf(Point(2240, 3470)), Point(28, 40)),
            "tarnogórski" to Data("śląskie", arrayOf(Point(2200, 3330)), Point(28, 40)),
            "Tychy" to Data("śląskie", arrayOf(Point(2290, 3600)), Point(28, 40)),
            "wodzisławski" to Data("śląskie", arrayOf(Point(2000, 3700)), Point(28, 40)),
            "Zabrze" to Data("śląskie", arrayOf(Point(2180, 3450)), Point(28, 40)),
            "zawierciański" to Data("śląskie", arrayOf(Point(2570, 3290)), Point(28, 40)),
            "Żory" to Data("śląskie", arrayOf(Point(2140, 3660)), Point(28, 40)),
            "żywiecki" to Data("śląskie", arrayOf(Point(2400, 3900)), Point(28, 40)),
    )}

    private fun Opolskie() : Map<String, Data>
    { return mapOf(
            "brzeski o." to Data("opolskie", arrayOf(Point(1570, 3070)), Point(28, 40)),
            "głubczycki" to Data("opolskie", arrayOf(Point(1780, 3600)), Point(28, 40)),
            "kędzierzyńsko-kozielski" to Data("opolskie", arrayOf(Point(1890, 3460)), Point(28, 40)),
            "kluczborski" to Data("opolskie", arrayOf(Point(1870, 2890)), Point(28, 40)),
            "krapkowicki" to Data("opolskie", arrayOf(Point(1810, 3340)), Point(28, 40)),
            "namysłowski" to Data("opolskie", arrayOf(Point(1680, 2880)), Point(28, 40)),
            "nyski" to Data("opolskie", arrayOf(Point(1490, 3330)), Point(28, 40)),
            "oleski" to Data("opolskie", arrayOf(Point(2050, 2970)), Point(28, 40)),
            "Opole" to Data("opolskie", arrayOf(Point(1780, 3180)), Point(28, 40)),
            "opolski" to Data("opolskie", arrayOf(Point(1860, 3120)), Point(28, 40)),
            "prudnicki" to Data("opolskie", arrayOf(Point(1650, 3400)), Point(28, 40)),
            "strzelecki" to Data("opolskie", arrayOf(Point(1960, 3290)), Point(28, 40)),
    )}

    private fun Dolnośląskie() : Map<String, Data>
    { return mapOf(
            "bolesławiecki" to Data("dolnośląskie", arrayOf(Point(680, 2680)), Point(10, 35)),
            "dzierżoniowski" to Data("dolnośląskie", arrayOf(Point(1220, 3120)), Point(10, 35)),
            "głogowski" to Data("dolnośląskie", arrayOf(Point(900, 2400)), Point(10, 35)),
            "górowski" to Data("dolnośląskie", arrayOf(Point(1110, 2430)), Point(10, 35)),
            "jaworski" to Data("dolnośląskie", arrayOf(Point(930, 2900)), Point(10, 35)),
            "Jelenia Góra" to Data("dolnośląskie", arrayOf(Point(760, 3010)), Point(10, 35)),
            "jeleniogórski" to Data("dolnośląskie", arrayOf(Point(680, 3000)), Point(10, 35)),
            "kamiennogórski" to Data("dolnośląskie", arrayOf(Point(880, 3110)), Point(10, 35)),
            "kłodzki" to Data("dolnośląskie", arrayOf(Point(1160, 3400)), Point(10, 35)),
            "Legnica" to Data("dolnośląskie", arrayOf(Point(960, 2770)), Point(10, 35)),
            "legnicki" to Data("dolnośląskie", arrayOf(Point(1040, 2770)), Point(10, 35)),
            "lubański" to Data("dolnośląskie", arrayOf(Point(550, 2850)), Point(10, 35)),
            "lubiński" to Data("dolnośląskie", arrayOf(Point(1020, 2600)), Point(10, 35)),
            "lwówecki" to Data("dolnośląskie", arrayOf(Point(680, 2880)), Point(10, 35)),
            "milicki" to Data("dolnośląskie", arrayOf(Point(1500, 2530)), Point(10, 35)),
            "oleśnicki" to Data("dolnośląskie", arrayOf(Point(1600, 2720)), Point(10, 35)),
            "oławski" to Data("dolnośląskie", arrayOf(Point(1500, 2960)), Point(10, 35)),
            "polkowicki" to Data("dolnośląskie", arrayOf(Point(860, 2570)), Point(10, 35)),
            "strzeliński" to Data("dolnośląskie", arrayOf(Point(1390, 3100)), Point(10, 35)),
            "średzki d." to Data("dolnośląskie", arrayOf(Point(1170, 2800)), Point(10, 35)),
            "świdnicki" to Data("dolnośląskie", arrayOf(Point(1090, 3000)), Point(10, 35)),
            "trzebnicki" to Data("dolnośląskie", arrayOf(Point(1330, 2630)), Point(10, 35)),
            "Wałbrzych" to Data("dolnośląskie", arrayOf(Point(1020, 3100)), Point(10, 35)),
            "wałbrzyski" to Data("dolnośląskie", arrayOf(Point(960, 3080)), Point(10, 35)),
            "wołowski" to Data("dolnośląskie", arrayOf(Point(1170, 2640)), Point(10, 35)),
            "Wrocław" to Data("dolnośląskie", arrayOf(Point(1350, 2840)), Point(10, 35)),
            "wrocławski" to Data("dolnośląskie", arrayOf(Point(1330, 2950)), Point(10, 35)),
            "ząbkowicki" to Data("dolnośląskie", arrayOf(Point(1300, 3260)), Point(10, 35)),
            "zgorzelecki" to Data("dolnośląskie", arrayOf(Point(490, 2700)), Point(10, 35)),
            "złotoryjski" to Data("dolnośląskie", arrayOf(Point(830, 2840)), Point(10, 35)),
    )}

    private fun Lubuskie() : Map<String, Data>
    { return mapOf(
            "gorzowski" to Data("lubuskie", arrayOf(Point(380, 1600)), Point(10, 35)),
            "Gorzów Wielkopolski" to Data("lubuskie", arrayOf(Point(520, 1600)), Point(10, 35)),
            "krośnieński l." to Data("lubuskie", arrayOf(Point(430, 2120)), Point(10, 35)),
            "międzyrzecki" to Data("lubuskie", arrayOf(Point(700, 1770)), Point(10, 35)),
            "nowosolski" to Data("lubuskie", arrayOf(Point(730, 2340)), Point(10, 35)),
            "słubicki" to Data("lubuskie", arrayOf(Point(300, 1860)), Point(10, 35)),
            "strzelecko-drezdenecki" to Data("lubuskie", arrayOf(Point(770, 1480)), Point(10, 35)),
            "sulęciński" to Data("lubuskie", arrayOf(Point(490, 1790)), Point(10, 35)),
            "świebodziński" to Data("lubuskie", arrayOf(Point(650, 1970)), Point(10, 35)),
            "wschowski" to Data("lubuskie", arrayOf(Point(940, 2280)), Point(10, 35)),
            "Zielona Góra" to Data("lubuskie", arrayOf(Point(680, 2220)), Point(10, 35)),
            "zielonogórski" to Data("lubuskie", arrayOf(Point(760, 2100)), Point(10, 35)),
            "żagański" to Data("lubuskie", arrayOf(Point(630, 2460)), Point(10, 35)),
            "żarski" to Data("lubuskie", arrayOf(Point(400, 2380)), Point(10, 35)),
    )}

    private fun Zachodniopomorskie() : Map<String, Data>
    { return mapOf(
            "białogardzki" to Data("zachodniopomorskie", arrayOf(Point(890, 640)), Point(0, 10)),
            "choszczeński" to Data("zachodniopomorskie", arrayOf(Point(700, 1300)), Point(0, 10)),
            "drawski" to Data("zachodniopomorskie", arrayOf(Point(860, 1000)), Point(0, 10)),
            "goleniowski" to Data("zachodniopomorskie", arrayOf(Point(360, 900)), Point(0, 10)),
            "gryficki" to Data("zachodniopomorskie", arrayOf(Point(520, 640)), Point(0, 10)),
            "gryfiński" to Data("zachodniopomorskie", arrayOf(Point(190, 1400)), Point(0, 10)),
            "kamieński" to Data("zachodniopomorskie", arrayOf(Point(360, 700)), Point(0, 10)),
            "kołobrzeski" to Data("zachodniopomorskie", arrayOf(Point(710, 540)), Point(0, 10)),
            "Koszalin" to Data("zachodniopomorskie", arrayOf(Point(990, 490)), Point(0, 10)),
            "koszaliński" to Data("zachodniopomorskie", arrayOf(Point(1120, 550)), Point(0, 10)),
            "łobeski" to Data("zachodniopomorskie", arrayOf(Point(660, 890)), Point(0, 10)),
            "myśliborski" to Data("zachodniopomorskie", arrayOf(Point(450, 1450)), Point(0, 10)),
            "policki" to Data("zachodniopomorskie", arrayOf(Point(160, 960)), Point(0, 10)),
            "pyrzycki" to Data("zachodniopomorskie", arrayOf(Point(390, 1280)), Point(0, 10)),
            "sławieński" to Data("zachodniopomorskie", arrayOf(Point(1160, 330)), Point(0, 10)),
            "stargardzki" to Data("zachodniopomorskie", arrayOf(Point(520, 1120)), Point(0, 10)),
            "Szczecin" to Data("zachodniopomorskie", arrayOf(Point(230, 1050)), Point(0, 10)),
            "szczecinecki" to Data("zachodniopomorskie", arrayOf(Point(1140, 840)), Point(0, 10)),
            "świdwiński" to Data("zachodniopomorskie", arrayOf(Point(840, 790)), Point(0, 10)),
            "Świnoujście" to Data("zachodniopomorskie", arrayOf(Point(210, 690), Point(80, 720)), Point(0, 10)),
            "wałecki" to Data("zachodniopomorskie", arrayOf(Point(1040, 1190)), Point(0, 10)),
    )}

    private fun Pomorskie() : Map<String, Data>
    { return mapOf(
            "bytowski" to Data("pomorskie", arrayOf(Point(1520, 540)), Point(25, 0)),
            "chojnicki" to Data("pomorskie", arrayOf(Point(1660, 760)), Point(25, 0)),
            "człuchowski" to Data("pomorskie", arrayOf(Point(1410, 840)), Point(25, 0)),
            "Gdańsk" to Data("pomorskie", arrayOf(Point(2100, 360)), Point(25, 0)),
            "gdański" to Data("pomorskie", arrayOf(Point(2080, 460)), Point(25, 0)),
            "Gdynia" to Data("pomorskie", arrayOf(Point(2050, 240)), Point(25, 0)),
            "kartuski" to Data("pomorskie", arrayOf(Point(1860, 400)), Point(25, 0)),
            "kościerski" to Data("pomorskie", arrayOf(Point(1790, 600)), Point(25, 0)),
            "kwidzyński" to Data("pomorskie", arrayOf(Point(2290, 850)), Point(25, 0)),
            "lęborski" to Data("pomorskie", arrayOf(Point(1680, 200)), Point(25, 0)),
            "malborski" to Data("pomorskie", arrayOf(Point(2300, 570)), Point(25, 0)),
            "nowodworski p." to Data("pomorskie", arrayOf(Point(2330, 430)), Point(25, 0)),
            "pucki" to Data("pomorskie", arrayOf(Point(1940, 50), Point(2187, 165)), Point(25, 0)),
            "Słupsk" to Data("pomorskie", arrayOf(Point(1360, 280)), Point(25, 0)),
            "słupski" to Data("pomorskie", arrayOf(Point(1490, 270)), Point(25, 0)),
            "Sopot" to Data("pomorskie", arrayOf(Point(2073, 305)), Point(25, 0)),
            "starogardzki" to Data("pomorskie", arrayOf(Point(2000, 750)), Point(25, 0)),
            "sztumski" to Data("pomorskie", arrayOf(Point(2400, 690)), Point(25, 0)),
            "tczewski" to Data("pomorskie", arrayOf(Point(2170, 660)), Point(25, 0)),
            "wejherowski" to Data("pomorskie", arrayOf(Point(1860, 200)), Point(25, 0)),
    )}

    private fun Warmińsko_mazurskie() : Map<String, Data>
    { return mapOf(
            "bartoszycki" to Data("warmińsko-mazurskie", arrayOf(Point(3050, 420)), Point(30, 5)),
            "braniewski" to Data("warmińsko-mazurskie", arrayOf(Point(2730, 400)), Point(30, 5)),
            "działdowski" to Data("warmińsko-mazurskie", arrayOf(Point(2730, 1180)), Point(30, 5)),
            "Elbląg" to Data("warmińsko-mazurskie", arrayOf(Point(2480, 510)), Point(30, 5)),
            "elbląski" to Data("warmińsko-mazurskie", arrayOf(Point(2550, 570)), Point(30, 5)),
            "ełcki" to Data("warmińsko-mazurskie", arrayOf(Point(3850, 790)), Point(30, 5)),
            "giżycki" to Data("warmińsko-mazurskie", arrayOf(Point(3600, 630)), Point(30, 5)),
            "gołdapski" to Data("warmińsko-mazurskie", arrayOf(Point(3820, 440)), Point(30, 5)),
            "iławski" to Data("warmińsko-mazurskie", arrayOf(Point(2520, 890)), Point(30, 5)),
            "kętrzyński" to Data("warmińsko-mazurskie", arrayOf(Point(3370, 500)), Point(30, 5)),
            "lidzbarski" to Data("warmińsko-mazurskie", arrayOf(Point(2930, 530)), Point(30, 5)),
            "mrągowski" to Data("warmińsko-mazurskie", arrayOf(Point(3360, 770)), Point(30, 5)),
            "nidzicki" to Data("warmińsko-mazurskie", arrayOf(Point(2980, 1120)), Point(30, 5)),
            "nowomiejski" to Data("warmińsko-mazurskie", arrayOf(Point(2550, 1070)), Point(30, 5)),
            "olecki" to Data("warmińsko-mazurskie", arrayOf(Point(3880, 610)), Point(30, 5)),
            "Olsztyn" to Data("warmińsko-mazurskie", arrayOf(Point(2960, 800)), Point(30, 5)),
            "olsztyński" to Data("warmińsko-mazurskie", arrayOf(Point(3070, 750)), Point(30, 5)),
            "ostródzki" to Data("warmińsko-mazurskie", arrayOf(Point(2730, 830)), Point(30, 5)),
            "piski" to Data("warmińsko-mazurskie", arrayOf(Point(3620, 900)), Point(30, 5)),
            "szczycieński" to Data("warmińsko-mazurskie", arrayOf(Point(3210, 1000)), Point(30, 5)),
            "węgorzewski" to Data("warmińsko-mazurskie", arrayOf(Point(3570, 480)), Point(30, 5)),
    )}

    private fun Podlaskie() : Map<String, Data>
    { return mapOf(
            "augustowski" to Data("podlaskie", arrayOf(Point(4170, 780)), Point(45, 15)),
            "białostocki" to Data("podlaskie", arrayOf(Point(4340, 1320)), Point(45, 15)),
            "Białystok" to Data("podlaskie", arrayOf(Point(4220, 1300)), Point(45, 15)),
            "bielski p." to Data("podlaskie", arrayOf(Point(4140, 1590)), Point(45, 15)),
            "grajewski" to Data("podlaskie", arrayOf(Point(3880, 960)), Point(45, 15)),
            "hajnowski" to Data("podlaskie", arrayOf(Point(4410, 1610)), Point(45, 15)),
            "kolneński" to Data("podlaskie", arrayOf(Point(3680, 1110)), Point(45, 15)),
            "Łomża" to Data("podlaskie", arrayOf(Point(3710, 1280)), Point(45, 15)),
            "łomżyński" to Data("podlaskie", arrayOf(Point(3780, 1250)), Point(45, 15)),
            "moniecki" to Data("podlaskie", arrayOf(Point(4040, 1080)), Point(45, 15)),
            "sejneński" to Data("podlaskie", arrayOf(Point(4270, 570)), Point(45, 15)),
            "siemiatycki" to Data("podlaskie", arrayOf(Point(4070, 1800)), Point(45, 15)),
            "sokólski" to Data("podlaskie", arrayOf(Point(4320, 1010)), Point(45, 15)),
            "suwalski" to Data("podlaskie", arrayOf(Point(4020, 500)), Point(45, 15)),
            "Suwałki" to Data("podlaskie", arrayOf(Point(4110, 570)), Point(45, 15)),
            "wysokomazowiecki" to Data("podlaskie", arrayOf(Point(3940, 1470)), Point(45, 15)),
            "zambrowski" to Data("podlaskie", arrayOf(Point(3790, 1400)), Point(45, 15)),
    )}

    private fun Lubelskie() : Map<String, Data>
    { return mapOf(
            "bialski" to Data("lubelskie", arrayOf(Point(4290, 2230)), Point(50, 30)),
            "Biała Podlaska" to Data("lubelskie", arrayOf(Point(4210, 2150)), Point(50, 30)),
            "biłgorajski" to Data("lubelskie", arrayOf(Point(4050, 3340)), Point(50, 30)),
            "Chełm" to Data("lubelskie", arrayOf(Point(4380, 2835)), Point(50, 30)),
            "chełmski" to Data("lubelskie", arrayOf(Point(4450, 2870)), Point(50, 30)),
            "hrubieszowski" to Data("lubelskie", arrayOf(Point(4530, 3100)), Point(50, 30)),
            "janowski" to Data("lubelskie", arrayOf(Point(3890, 3150)), Point(50, 30)),
            "krasnostawski" to Data("lubelskie", arrayOf(Point(4200, 2950)), Point(50, 30)),
            "kraśnicki" to Data("lubelskie", arrayOf(Point(3740, 3000)), Point(50, 30)),
            "lubartowski" to Data("lubelskie", arrayOf(Point(3910, 2530)), Point(50, 30)),
            "lubelski" to Data("lubelskie", arrayOf(Point(3840, 2800)), Point(50, 30)),
            "Lublin" to Data("lubelskie", arrayOf(Point(3950, 2760)), Point(50, 30)),
            "łęczyński" to Data("lubelskie", arrayOf(Point(4130, 2700)), Point(50, 30)),
            "łukowski" to Data("lubelskie", arrayOf(Point(3780, 2260)), Point(50, 30)),
            "opolski l." to Data("lubelskie", arrayOf(Point(3680, 2820)), Point(50, 30)),
            "parczewski" to Data("lubelskie", arrayOf(Point(4160, 2450)), Point(50, 30)),
            "puławski" to Data("lubelskie", arrayOf(Point(3700, 2620)), Point(50, 30)),
            "radzyński" to Data("lubelskie", arrayOf(Point(3990, 2330)), Point(50, 30)),
            "rycki" to Data("lubelskie", arrayOf(Point(3670, 2440)), Point(50, 30)),
            "świdnicki l." to Data("lubelskie", arrayOf(Point(4050, 2800)), Point(50, 30)),
            "tomaszowski l." to Data("lubelskie", arrayOf(Point(4420, 3330)), Point(50, 30)),
            "włodawski" to Data("lubelskie", arrayOf(Point(4310, 2540)), Point(50, 30)),
            "zamojski" to Data("lubelskie", arrayOf(Point(4350, 3140)), Point(50, 30)),
            "Zamość" to Data("lubelskie", arrayOf(Point(4265, 3160)), Point(50, 30)),
    )}

    private fun Kujawsko_pomorskie() : Map<String, Data>
    { return mapOf(
            "aleksandrowski" to Data("kujawsko-pomorskie", arrayOf(Point(2160, 1550)), Point(25, 15)),
            "brodnicki" to Data("kujawsko-pomorskie", arrayOf(Point(2480, 1190)), Point(25, 15)),
            "bydgoski" to Data("kujawsko-pomorskie", arrayOf(Point(1790, 1180)), Point(25, 15)),
            "Bydgoszcz" to Data("kujawsko-pomorskie", arrayOf(Point(1840, 1300)), Point(25, 15)),
            "chełmiński" to Data("kujawsko-pomorskie", arrayOf(Point(2050, 1180)), Point(25, 15)),
            "golubsko-dobrzyński" to Data("kujawsko-pomorskie", arrayOf(Point(2300, 1330)), Point(25, 15)),
            "Grudziądz" to Data("kujawsko-pomorskie", arrayOf(Point(2170, 1050)), Point(25, 15)),
            "grudziądzki" to Data("kujawsko-pomorskie", arrayOf(Point(2290, 1040)), Point(25, 15)),
            "inowrocławski" to Data("kujawsko-pomorskie", arrayOf(Point(1950, 1540)), Point(25, 15)),
            "lipnowski" to Data("kujawsko-pomorskie", arrayOf(Point(2420, 1540)), Point(25, 15)),
            "mogileński" to Data("kujawsko-pomorskie", arrayOf(Point(1830, 1680)), Point(25, 15)),
            "nakielski" to Data("kujawsko-pomorskie", arrayOf(Point(1600, 1320)), Point(25, 15)),
            "radziejowski" to Data("kujawsko-pomorskie", arrayOf(Point(2090, 1710)), Point(25, 15)),
            "rypiński" to Data("kujawsko-pomorskie", arrayOf(Point(2460, 1380)), Point(25, 15)),
            "sępoleński" to Data("kujawsko-pomorskie", arrayOf(Point(1600, 1080)), Point(25, 15)),
            "świecki" to Data("kujawsko-pomorskie", arrayOf(Point(2040, 1000)), Point(25, 15)),
            "Toruń" to Data("kujawsko-pomorskie", arrayOf(Point(2100, 1390)), Point(25, 15)),
            "toruński" to Data("kujawsko-pomorskie", arrayOf(Point(2080, 1300)), Point(25, 15)),
            "tucholski" to Data("kujawsko-pomorskie", arrayOf(Point(1790, 970)), Point(25, 15)),
            "wąbrzeski" to Data("kujawsko-pomorskie", arrayOf(Point(2250, 1200)), Point(25, 15)),
            "Włocławek" to Data("kujawsko-pomorskie", arrayOf(Point(2320, 1670)), Point(25, 15)),
            "włocławski" to Data("kujawsko-pomorskie", arrayOf(Point(2280, 1790), Point(2330, 1630)), Point(25, 15)),
            "żniński" to Data("kujawsko-pomorskie", arrayOf(Point(1680, 1540)), Point(25, 15)),
    )}

    private fun Łódzkie() : Map<String, Data>
    { return mapOf(
            "bełchatowski" to Data("łódzkie", arrayOf(Point(2410, 2660)), Point(30, 30)),
            "brzeziński" to Data("łódzkie", arrayOf(Point(2660, 2290)), Point(30, 30)),
            "kutnowski" to Data("łódzkie", arrayOf(Point(2470, 1970)), Point(30, 30)),
            "łaski" to Data("łódzkie", arrayOf(Point(2330, 2500)), Point(30, 30)),
            "łęczycki" to Data("łódzkie", arrayOf(Point(2340, 2090)), Point(30, 30)),
            "łowicki" to Data("łódzkie", arrayOf(Point(2690, 2070)), Point(30, 30)),
            "łódzki wschodni" to Data("łódzkie", arrayOf(Point(2570, 2420)), Point(30, 30)),
            "Łódź" to Data("łódzkie", arrayOf(Point(2500, 2340)), Point(30, 30)),
            "opoczyński" to Data("łódzkie", arrayOf(Point(2850, 2610)), Point(30, 30)),
            "pabianicki" to Data("łódzkie", arrayOf(Point(2410, 2420)), Point(30, 30)),
            "pajęczański" to Data("łódzkie", arrayOf(Point(2290, 2800)), Point(30, 30)),
            "piotrkowski" to Data("łódzkie", arrayOf(Point(2600, 2700)), Point(30, 30)),
            "Piotrków Trybunalski" to Data("łódzkie", arrayOf(Point(2600, 2610)), Point(30, 30)),
            "poddębicki" to Data("łódzkie", arrayOf(Point(2250, 2240)), Point(30, 30)),
            "radomszczański" to Data("łódzkie", arrayOf(Point(2550, 2880)), Point(30, 30)),
            "rawski" to Data("łódzkie", arrayOf(Point(2910, 2330)), Point(30, 30)),
            "sieradzki" to Data("łódzkie", arrayOf(Point(2090, 2480)), Point(30, 30)),
            "Skierniewice" to Data("łódzkie", arrayOf(Point(2820, 2190)), Point(30, 30)),
            "skierniewicki" to Data("łódzkie", arrayOf(Point(2760, 2250)), Point(30, 30)),
            "tomaszowski" to Data("łódzkie", arrayOf(Point(2770, 2460)), Point(30, 30)),
            "wieluński" to Data("łódzkie", arrayOf(Point(2080, 2750)), Point(30, 30)),
            "wieruszowski" to Data("łódzkie", arrayOf(Point(1930, 2680)), Point(30, 30)),
            "zduńskowolski" to Data("łódzkie", arrayOf(Point(2240, 2410)), Point(30, 30)),
            "zgierski" to Data("łódzkie", arrayOf(Point(2460, 2230)), Point(30, 30)),
    )}

    private fun Wielkopolskie() : Map<String, Data>
    { return mapOf(
            "chodzieski" to Data("wielkopolskie", arrayOf(Point(1340, 1430)), Point(20, 20)),
            "czarnkowsko-trzcianecki" to Data("wielkopolskie", arrayOf(Point(1050, 1470)), Point(20, 20)),
            "gnieźnieński" to Data("wielkopolskie", arrayOf(Point(1590, 1730)), Point(20, 20)),
            "gostyński" to Data("wielkopolskie", arrayOf(Point(1370, 2280)), Point(20, 20)),
            "grodziski w." to Data("wielkopolskie", arrayOf(Point(1030, 2020)), Point(20, 20)),
            "jarociński" to Data("wielkopolskie", arrayOf(Point(1580, 2180)), Point(20, 20)),
            "kaliski" to Data("wielkopolskie", arrayOf(Point(1900, 2280)), Point(20, 20)),
            "Kalisz" to Data("wielkopolskie", arrayOf(Point(1860, 2360)), Point(20, 20)),
            "kępiński" to Data("wielkopolskie", arrayOf(Point(1780, 2720)), Point(20, 20)),
            "kolski" to Data("wielkopolskie", arrayOf(Point(2170, 1980)), Point(20, 20)),
            "Konin" to Data("wielkopolskie", arrayOf(Point(1940, 1950)), Point(20, 20)),
            "koniński" to Data("wielkopolskie", arrayOf(Point(1950, 1850)), Point(20, 20)),
            "kościański" to Data("wielkopolskie", arrayOf(Point(1200, 2100)), Point(20, 20)),
            "krotoszyński" to Data("wielkopolskie", arrayOf(Point(1550, 2350)), Point(20, 20)),
            "leszczyński" to Data("wielkopolskie", arrayOf(Point(1220, 2250)), Point(20, 20)),
            "Leszno" to Data("wielkopolskie", arrayOf(Point(1150, 2290)), Point(20, 20)),
            "międzychodzki" to Data("wielkopolskie", arrayOf(Point(870, 1700)), Point(20, 20)),
            "nowotomyski" to Data("wielkopolskie", arrayOf(Point(930, 1900)), Point(20, 20)),
            "obornicki" to Data("wielkopolskie", arrayOf(Point(1280, 1630)), Point(20, 20)),
            "ostrowski w." to Data("wielkopolskie", arrayOf(Point(1750, 2420)), Point(20, 20)),
            "ostrzeszowski" to Data("wielkopolskie", arrayOf(Point(1820, 2590)), Point(20, 20)),
            "pilski" to Data("wielkopolskie", arrayOf(Point(1310, 1290)), Point(20, 20)),
            "pleszewski" to Data("wielkopolskie", arrayOf(Point(1740, 2230)), Point(20, 20)),
            "Poznań" to Data("wielkopolskie", arrayOf(Point(1300, 1850)), Point(20, 20)),
            "poznański" to Data("wielkopolskie", arrayOf(Point(1450, 1850)), Point(20, 20)),
            "rawicki" to Data("wielkopolskie", arrayOf(Point(1310, 2440)), Point(20, 20)),
            "słupecki" to Data("wielkopolskie", arrayOf(Point(1750, 1920)), Point(20, 20)),
            "szamotulski" to Data("wielkopolskie", arrayOf(Point(1100, 1700)), Point(20, 20)),
            "średzki" to Data("wielkopolskie", arrayOf(Point(1490, 2010)), Point(20, 20)),
            "śremski" to Data("wielkopolskie", arrayOf(Point(1370, 2120)), Point(20, 20)),
            "turecki" to Data("wielkopolskie", arrayOf(Point(2030, 2150)), Point(20, 20)),
            "wągrowiecki" to Data("wielkopolskie", arrayOf(Point(1470, 1520)), Point(20, 20)),
            "wolsztyński" to Data("wielkopolskie", arrayOf(Point(950, 2110)), Point(20, 20)),
            "wrzesiński" to Data("wielkopolskie", arrayOf(Point(1620, 1940)), Point(20, 20)),
            "złotowski" to Data("wielkopolskie", arrayOf(Point(1320, 1090)), Point(20, 20)),
    )}

    private fun Mazowieckie() : Map<String, Data>
    { return mapOf(
            "białobrzeski" to Data("mazowieckie", arrayOf(Point(3190, 2470)), Point(45, 20)),
            "ciechanowski" to Data("mazowieckie", arrayOf(Point(3050, 1500)), Point(45, 20)),
            "garwoliński" to Data("mazowieckie", arrayOf(Point(3520, 2300)), Point(45, 20)),
            "gostyniński" to Data("mazowieckie", arrayOf(Point(2480, 1860)), Point(45, 20)),
            "grodziski" to Data("mazowieckie", arrayOf(Point(3020, 2090)), Point(45, 20)),
            "grójecki" to Data("mazowieckie", arrayOf(Point(3150, 2300)), Point(45, 20)),
            "kozienicki" to Data("mazowieckie", arrayOf(Point(3390, 2460)), Point(45, 20)),
            "legionowski" to Data("mazowieckie", arrayOf(Point(3200, 1840)), Point(45, 20)),
            "lipski" to Data("mazowieckie", arrayOf(Point(3500, 2810)), Point(45, 20)),
            "łosicki" to Data("mazowieckie", arrayOf(Point(4070, 2000)), Point(45, 20)),
            "makowski" to Data("mazowieckie", arrayOf(Point(3310, 1450)), Point(45, 20)),
            "miński" to Data("mazowieckie", arrayOf(Point(3520, 2020)), Point(45, 20)),
            "mławski" to Data("mazowieckie", arrayOf(Point(2910, 1370)), Point(45, 20)),
            "nowodworski" to Data("mazowieckie", arrayOf(Point(3040, 1850)), Point(45, 20)),
            "ostrołęcki" to Data("mazowieckie", arrayOf(Point(3420, 1220)), Point(45, 20)),
            "Ostrołęka" to Data("mazowieckie", arrayOf(Point(3490, 1350)), Point(45, 20)),
            "ostrowski" to Data("mazowieckie", arrayOf(Point(3650, 1580)), Point(45, 20)),
            "otwocki" to Data("mazowieckie", arrayOf(Point(3370, 2100)), Point(45, 20)),
            "piaseczyński" to Data("mazowieckie", arrayOf(Point(3220, 2160)), Point(45, 20)),
            "Płock" to Data("mazowieckie", arrayOf(Point(2613, 1760)), Point(45, 20)),
            "płocki" to Data("mazowieckie", arrayOf(Point(2700, 1715)), Point(45, 20)),
            "płoński" to Data("mazowieckie", arrayOf(Point(2900, 1700)), Point(45, 20)),
            "pruszkowski" to Data("mazowieckie", arrayOf(Point(3140, 2050)), Point(45, 20)),
            "przasnyski" to Data("mazowieckie", arrayOf(Point(3160, 1290)), Point(45, 20)),
            "przysuski" to Data("mazowieckie", arrayOf(Point(3020, 2620)), Point(45, 20)),
            "pułtuski" to Data("mazowieckie", arrayOf(Point(3220, 1650)), Point(45, 20)),
            "Radom" to Data("mazowieckie", arrayOf(Point(3280, 2640)), Point(45, 20)),
            "radomski" to Data("mazowieckie", arrayOf(Point(3300, 2730)), Point(45, 20)),
            "Siedlce" to Data("mazowieckie", arrayOf(Point(3800, 2050)), Point(45, 20)),
            "siedlecki" to Data("mazowieckie", arrayOf(Point(3880, 2000)), Point(45, 20)),
            "sierpecki" to Data("mazowieckie", arrayOf(Point(2620, 1530)), Point(45, 20)),
            "sochaczewski" to Data("mazowieckie", arrayOf(Point(2850, 1980)), Point(45, 20)),
            "sokołowski" to Data("mazowieckie", arrayOf(Point(3830, 1790)), Point(45, 20)),
            "szydłowiecki" to Data("mazowieckie", arrayOf(Point(3160, 2739)), Point(45, 20)),
            "Warszawa" to Data("mazowieckie", arrayOf(Point(3270, 1990)), Point(45, 20)),
            "warszawski zachodni" to Data("mazowieckie", arrayOf(Point(3080, 1970)), Point(45, 20)),
            "węgrowski" to Data("mazowieckie", arrayOf(Point(3630, 1800)), Point(45, 20)),
            "wołomiński" to Data("mazowieckie", arrayOf(Point(3370, 1880)), Point(45, 20)),
            "wyszkowski" to Data("mazowieckie", arrayOf(Point(3430, 1660)), Point(45, 20)),
            "zwoleński" to Data("mazowieckie", arrayOf(Point(3500, 2670)), Point(45, 20)),
            "żuromiński" to Data("mazowieckie", arrayOf(Point(2690, 1360)), Point(45, 20)),
            "żyrardowski" to Data("mazowieckie", arrayOf(Point(2930, 2150)), Point(45, 20)),
    )}
}