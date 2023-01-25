package com.juliopg.financeapp

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.geometry.Offset
import com.juliopg.financeapp.componentes.MainScreen
import com.juliopg.financeapp.model.Stock
import com.juliopg.financeapp.ui.theme.BLUE200
import com.juliopg.financeapp.ui.theme.FinanceAppTheme
import com.juliopg.financeapp.ui.theme.PINK200
import com.juliopg.financeapp.ui.theme.RED600
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*

private val months = listOf(
  "Jan",
  "Feb",
  "Mar",
  "Apr",
  "May",
  "Jun",
  "Jul",
  "Aug",
  "Sep",
  "Oct",
  "Nov",
  "Dec"
)

private val stocks =  listOf(
  Stock(
    company = "Dribble",
    icon = R.drawable.ic_dribbble_logo,
    color = PINK200,
    price = 66.43,
    date = System.currentTimeMillis()
  ),
  Stock(
    company = "Skype",
    icon = R.drawable.ic_skype_logo,
    color = BLUE200,
    price = -32.60,
    date = System.currentTimeMillis()
  ),
  Stock(
    company = "Youtube Premium",
    icon = R.drawable.ic_youtube_logo,
    color = RED600,
    price = -12.00,
    date = System.currentTimeMillis()
  )
)

class MainActivity : ComponentActivity() {
  @OptIn(ExperimentalMaterialApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val points = parse(this)
    val randomStartPoint = points.shuffled().first()

    val time =  System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("MMM dd, yyy", Locale.ROOT)
    val date = dateFormat.format(time).capitalize(Locale.ROOT)

    setContent {
      FinanceAppTheme {
        MainScreen(
          randomStartPoint = randomStartPoint,
          points = points,
          months = months,
          stockList = stocks,
          date = date )
        }
      }
    }
  }

private fun parse(context: Context): List<Offset>{
  val json = context.assets.open("coordinator.json").bufferedReader().use { it.readLine() }
  val array = JSONArray(json)
  val points = mutableListOf<Offset>()

  for (i in 0 until array.length()){
    val coordinate = array.optJSONArray(i)
    val x = coordinate.optDouble(0).toFloat()
    val y = coordinate.optDouble(1).toFloat()
    points += Offset(x, y)
  }
  return points
}
