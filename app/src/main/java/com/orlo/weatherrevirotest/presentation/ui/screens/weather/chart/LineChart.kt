package com.orlo.weatherrevirotest.presentation.ui.screens.weather.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.orlo.weatherrevirotest.R
import com.orlo.weatherrevirotest.presentation.ui.theme.orange

fun LondonWeather(): List<Point> {
    return listOf(
        Point(00f, 15f),
        Point(01f, 16f),
        Point(02f, 16f),
        Point(03f, 16f),
        Point(04f, 12f),
        Point(05f, 12f),
        Point(06f, 13f),
        Point(07f, 18f),
        Point(08f, 20f),
        Point(09f, 20f),
        Point(10f, 20f),
        Point(11f, 25f),
        Point(12f, 28f),
        Point(13f, 27f),
        Point(14f, 26f),
        Point(15f, 24f),
        Point(16f, 22f),
        Point(17f, 20f)
    )
}

val pointsData: List<Point> = LondonWeather()

val steps = 18

val xAxisData = AxisData.Builder()
    .axisStepSize(100.dp)

    .backgroundColor(Color.Transparent)
    .steps(pointsData.size - 1)
//    .labelData { i -> i.toString() }
//    .labelAndAxisLinePadding(15.dp)
//    .axisLineColor(orange)
//    .axisLabelColor(orange)
    .build()

val yAxisData = AxisData.Builder()
    .steps(steps)
    .backgroundColor(Color.Transparent)
    .labelAndAxisLinePadding(20.dp)
//    .labelData { i -> i.toString() }
//    .axisLineColor(orange)
//    .axisLabelColor(orange)
    .build()

val popUpLabel: (Float, Float) -> (String) = { x, y ->
    val xLabel = "time : ${String.format("%.2f", x)} "
    val yLabel = "temp: ${y.toInt()}°C"
    "$xLabel $yLabel"
}

val lineChartData = LineChartData(
    linePlotData = LinePlotData(
        lines = listOf(
            Line(
                dataPoints = pointsData,
                LineStyle(color = orange, width = 4f),
                intersectionPoint = null,
//                IntersectionPoint(color = Color.White, radius = 3.dp),
                SelectionHighlightPoint(orange),
                ShadowUnderLine(),
                SelectionHighlightPopUp(popUpLabel = popUpLabel)
            )
        ),
    ),
    backgroundColor = Color.Transparent,
    xAxisData = xAxisData,
    yAxisData = yAxisData
)


@Preview
@Composable
fun ForecastChart(
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp, top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.7f),
            contentColor = Color.White
        )
    ) {
        Column(
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.ic_watch), contentDescription = null)
                Spacer(modifier = Modifier.padding(start = 8.dp))
                Text(text = "24-hours forecast")
            }
            LineChartWeather(
                modifier = Modifier
                    .height(200.dp)
            )
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = { /*TODO*/ }) {
                Text(text = "5-day forecast")
            }
        }
    }

}



