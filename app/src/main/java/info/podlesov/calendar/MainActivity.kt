package info.podlesov.calendar

import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.text.Layout
import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ProgressBar
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import androidx.core.view.children
import androidx.core.view.get
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    var executorService: ExecutorService = Executors.newFixedThreadPool(1)
    var mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val date = findViewById<TextView>(R.id.currentDate);
        val formatter = SimpleDateFormat("dd MMMM", Locale.forLanguageTag("RU"))
        date.text = formatter.format(Date())

        val tableView = findViewById<TableLayout>(R.id.resultTableView);

        tableView.isStretchAllColumns = true
        tableView.visibility = View.GONE
        var tag = 0;
        val firstRow = TableRow(this)

        listOf("Янв", "Фев", "Март", "Апр", "Май", "Июнь", "").map {
            val tv = TextView(this)
            tv.text = it
            tv.tag = tag++;
            tv.minEms = 3;
            tv.minLines = 3;
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv.gravity = Gravity.CENTER_VERTICAL + Gravity.CENTER_HORIZONTAL;
            tv
        }.forEach {
            firstRow.addView(it)
        }
        tableView.addView(firstRow)

        val secondRow = TableRow(this)
        listOf("Июль", "Авг", "Сент", "Окт", "Нояб", "Дек", "").map {
            val tv = TextView(this)
            tv.text = it
            tv.tag = tag++;
            tv.minLines = 3;
            tv.minEms = 3;
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            tv.gravity = Gravity.CENTER_VERTICAL + Gravity.CENTER_HORIZONTAL;
            tv
        }.forEach {
            secondRow.addView(it)
        }
        tableView.addView(secondRow)

        var start = 1;

        while (start < 31) {
            val row = TableRow(this)
            IntRange(start, start + 6).filter { it <=31 }.map { it.toString()}.map {
                val tv = TextView(this)
                tv.text = it;
                tv.tag = tag++;
                tv.minEms = 3;
                tv.minLines = 3;
                tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
                tv.gravity = Gravity.CENTER_VERTICAL + Gravity.CENTER_HORIZONTAL;

                tv
            }.forEach {
                row.addView(it)
            }
            tableView.addView(row)
            start += 7
        }

        executorService.execute {
            try {
                val result: Result<SolutionElement> = Result.success(SolutionElement.findTodaySolution())
                notifyResult(result)
            } catch (e: Exception) {
                notifyResult(Result.failure(e))
            }
        }
    }

    fun notifyResult(
        result: Result<SolutionElement>
    ) {
        mainThreadHandler.post() {
            val tableView = findViewById<TableLayout>(R.id.resultTableView);
            tableView.visibility = View.VISIBLE
            val progressBar = findViewById<ProgressBar>(R.id.progressBar);
            progressBar.visibility = View.GONE
            val colors = listOf(Color.RED, Color.rgb(255, 165, 0), Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA, Color.LTGRAY, Color.WHITE)

            val field = result.getOrThrow().field.field;
            var tag = 0;

            for (x in 0..6) {
                for (y in 0..6) {
                    val view = tableView.findViewWithTag<TextView>(y*7+x)
                    view?.setBackgroundColor(colors.get(field[x][y].toInt() - 1))
                }
            }
        }
    }

}