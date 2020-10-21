package com.example.schoolbattle.engine

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.TextView
import com.example.schoolbattle.R
import com.example.schoolbattle.RATING
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.roundToInt

// Константы рейтинга_______________________________________________________________________________
var colorsRating: List<Int> = listOf(
    Color.rgb(0, 0, 0),
    Color.rgb(200, 200, 200),
    Color.rgb(180, 200, 200),
    Color.rgb(220, 160, 220),
    Color.rgb(100, 200, 250),
    Color.GREEN,
    Color.rgb(255, 180, 5),
    Color.rgb(255, 0, 196),
    Color.RED) // Colors
var colorList: List<Int> = listOf(0, 700, 1000, 1300, 1600, 1900, 2200, 2500, 1000000) // Points
//__________________________________________________________________________________________________

fun colorByRating(r: Int): Int {
    for (i in 0..8) {
        if (r < colorList[i]) {
            return colorsRating[i]
        }
    }
    return colorsRating.last()
}

@SuppressLint("SetTextI18n")
fun addRatingAndColorToName(name: String, nameView: TextView) {
    nameView.text = "$name($RATING)"
    nameView.setTextColor(colorByRating(RATING))
}

@ExperimentalStdlibApi
fun updateGraphColors(graph: GraphView, xMax: Double, yMax: Double) { //Update Graph Colors
    val a: MutableList<Int> = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)
    for (i in colorList.reversed()) {
        if (i > yMax) {
            a.removeLast()
            continue
        }
    }
    a.add(a.last() + 1)
    for (i in a.reversed()) {
        val series: LineGraphSeries<DataPoint> = LineGraphSeries(arrayOf(
            DataPoint(0.0, colorList[i].toDouble()),
            DataPoint(xMax, colorList[i].toDouble())
        ))
        series.thickness = 0
        series.backgroundColor = colorsRating[i]
        series.isDrawBackground = true
        graph.addSeries(series)
    }
}

fun updateRating(u0: Int, u1: Int, result: Double) : Pair<Int, Int> { //Rating Change Formula
    val koff = 2.5 * kotlin.math.abs((u1.toDouble() + u0) / 2.0) /  kotlin.math.abs(u1.toDouble() * u0).pow(0.5)

    val seed0 = 1.0 / (1.0 + 10.0.pow((u0 - u1).toDouble() / 400.0)) + 1.0
    val seed1 = 1.0 / (1.0 + 10.0.pow((u1 - u0).toDouble() / 400.0)) + 1.0
    val geom0 = (seed0 * (2 - result)).pow(0.5)
    val geom1 = (seed1 * (result + 1)).pow(0.5)
    var l = 0.0
    var r = 1e5
    for (i in 0..20) {
        val m = (r + l) / 2
        if (geom0 > 1.0 / (1.0 + 10.0.pow((m - u1) / 400.0)) + 1.0) {
            r = m
        } else {
            l = m
        }
    }
    val r0 = (u0.toDouble() + (l - u0) / koff)
    l = 0.0
    r = 1e5
    for (i in 0..20) {
        val m = (r + l) / 2
        if (geom1 > 1.0 / (1.0 + 10.0.pow((m - u0) / 400.0)) + 1.0) {
            r = m
        } else {
            l = m
        }
    }
    val r1 = (u1.toDouble() + (l - u1) / koff)
    return Pair((r0 * (u0 + u1) / (r0 + r1)).roundToInt(), (r1 * (u0 + u1) / (r0 + r1)).roundToInt())
}

class RatingGraph(val activity: Activity?) {
    @ExperimentalStdlibApi
    fun buildGraph() {
        activity?.window?.statusBarColor = colorByRating(Color.BLACK)
        var series: PointsGraphSeries<DataPoint> = PointsGraphSeries(arrayOf())
        var series2: LineGraphSeries<DataPoint> = LineGraphSeries(arrayOf())
        val graph = activity?.findViewById<GraphView>(R.id.graph)
        graph?.viewport?.setMinX(0.0)
        graph?.viewport?.setMaxX(8.0)
        graph?.viewport?.setMinY(0.0)
        graph?.viewport?.setMaxY(1600.0)
        graph?.viewport?.isYAxisBoundsManual = true
        graph?.viewport?.isXAxisBoundsManual = true
        if (graph != null) {
            updateGraphColors(graph, 8.0, 1600.0)
        }
        graph?.addSeries(series2)
        graph?.addSeries(series)
        graph?.getGridLabelRenderer()?.setVerticalLabelsColor(Color.WHITE);
        graph?.getGridLabelRenderer()?.setHorizontalLabelsColor(Color.WHITE);

    }

    @ExperimentalStdlibApi
    fun updateRating(HISTORY: MutableList<Int>) {
        val data: Array<DataPoint> = Array(HISTORY.size) {i -> DataPoint(i.toDouble(), HISTORY[i].toDouble())}
        val graph = activity?.findViewById<GraphView>(R.id.graph)
        val series = PointsGraphSeries(data)
        val series2 = LineGraphSeries(data)
        series.size = 10f
        series.color = Color.BLACK
        series2.color = Color.BLACK
        graph?.clearFocus()
        graph?.viewport?.setMinX(0.0)
        graph?.viewport?.setMaxX(max(4.0, HISTORY.size.toDouble()))
        graph?.viewport?.setMinY(0.0)
        var mxY = 0
        for (i in HISTORY) mxY = max(mxY, i)
        if (graph != null) {
            updateGraphColors(graph, max(4.0, HISTORY.size.toDouble()), mxY.toDouble() + 300.0)
        }
        graph?.addSeries(series2)
        graph?.addSeries(series)
        mxY.toDouble().let { graph?.viewport?.setMaxY(it + 300.0) }
        graph?.viewport?.isYAxisBoundsManual = true
        graph?.viewport?.isXAxisBoundsManual = true
    }
}