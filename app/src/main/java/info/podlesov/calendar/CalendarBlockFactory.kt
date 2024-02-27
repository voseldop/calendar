package info.podlesov.calendar

class CalendarBlockFactory {
    companion object {
        fun createFromText(text: String, id: Byte): CalendarBlock {
            val rows: Array<Array<Boolean>> = text.split("\n")
                    .map { it.trim() }
                    .map() { row -> row.toCharArray().map { it -> it == 'X' }.toTypedArray()}.toTypedArray()
            val width = rows[0].size
            val height = rows.size
            val field = Array(width, { Array(height, {false}) })
            for (x in 0 until width) {
                for (y in 0 until height) {
                    field[x][y] = rows[y][x]
                }
            }
            return SimpleCalendarBlock(id, field);
        }

        fun b1() = createFromText("""
            XXX
            OXX
        """.trimIndent(), 1)
        fun b2() = createFromText("""
            XXX
            XXX
        """.trimIndent(), 2)
        fun b3() = createFromText("""
            XXX
            XOX
        """.trimIndent(), 3)
        fun b4() = createFromText("""
            OOX
            XXX
            XOO
        """.trimIndent(), 4)
        fun b5() = createFromText("""
            OOXO
            XXXX
        """.trimIndent(), 5)

        fun b6() = createFromText("""
            OXXX
            XXOO
        """.trimIndent(), 6)

        fun b7() = createFromText("""
            OOX
            OOX
            XXX
        """.trimIndent(), 7)

        fun b8() = createFromText("""
            OOOX
            XXXX
        """.trimIndent(), 8)

        fun allBlocks() = listOf(b1(), b2(), b3(), b4(), b5(), b6(), b7(), b8())
    }
}