package info.podlesov.calendar

import java.util.*

class DailyCalendarPuzzleField: CalendarPuzzleField {
    constructor(): super() {
        val calendar = Calendar.getInstance()
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        field[month%6][month/6] = 9;
        field[(day-1)%7][2+(day-1)/7] = 9;
    }
}