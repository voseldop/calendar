package info.podlesov.calendar

interface CalendarBlock {
    fun canApply(field: CalendarPuzzleField, x: Int, y: Int, rotation: Int = 0): Boolean

    fun apply(field: CalendarPuzzleField, x: Int, y: Int, rotation: Int = 0)

    fun undo(field: CalendarPuzzleField, x: Int, y: Int, rotation: Int = 0)

    fun toString(rotation: Int): String
}