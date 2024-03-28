package com.orlo.weatherrevirotest.presentation.ui.screens.weather.chart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.orlo.weatherrevirotest.presentation.ui.theme.orange

@Composable
fun LineChartWeather(modifier: Modifier) {
    val steps = 5
    val pointData = LondonWeather()

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(Color.Transparent)
        .steps(pointData.size - 1)
//        .labelData { i -> i.toString() }
        .axisLineColor(Color.Transparent)
//        .axisLabelColor(MaterialTheme.colorScheme.tertiary)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(20.dp)
//        .labelData { i ->
//            val yScale = 100 / steps
//            (i * yScale).toString()
//        }
        .axisLineColor(Color.Transparent)
        .axisLabelColor(orange)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointData,
                    LineStyle(
                        color = orange,
//                        lineType = LineType.SmoothCurve(isDotted = true),
                    ),
                    IntersectionPoint(
                        color = Color.White
                    ),
                    SelectionHighlightPoint(
                        color = orange.copy(alpha = 0.5f)
                    ),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                orange,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            )
        ),
//        backgroundColor = MaterialTheme.colorScheme.surface,
        backgroundColor = Color.Transparent,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
    )

    LineChart(
        modifier = modifier,
        lineChartData = lineChartData
    )

}

