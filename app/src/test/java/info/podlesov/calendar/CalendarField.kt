package info.podlesov.calendar

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CalendarField {
    @Test
    fun emptyFieldSimpleBlock() {
        val calendar = CalendarPuzzleField();
        val block = SimpleCalendarBlock();
        assertTrue(block.canApply(calendar, 0, 0))
        assertTrue(block.canApply(calendar, 2, 2))
        assertTrue(block.canApply(calendar, 0, 5))

        assertFalse(block.canApply(calendar, 1, 6))
        assertFalse(block.canApply(calendar, 2, 5))
        assertFalse(block.canApply(calendar, 0, 6))
        assertFalse(block.canApply(calendar, 6, 0))
        assertFalse(block.canApply(calendar, 6, 6))

        calendar.dump()
        block.apply(calendar, 0, 0)
        calendar.dump()
        block.undo(calendar, 0, 0)
        calendar.dump()
    }

    @Test
    fun differentSimpleBlocks() {
        val calendar = CalendarPuzzleField();
        val block1 = CalendarBlockFactory.createFromText(
                """
                    XX
                    XO
                    XO
                """.trimIndent(), 1
        );

        val block2 = CalendarBlockFactory.createFromText(
                """
                    OXO
                    XXX
                """.trimIndent(), 2
        );

        val block3 = CalendarBlockFactory.createFromText(
                """
                    OOOX
                    OOOX
                    XXXX
                """.trimIndent(), 3
        );

        calendar.dump()
        assertTrue(block1.canApply(calendar, 0, 0))
        block1.apply(calendar, 0, 0)
        calendar.dump()
        assertTrue(block2.canApply(calendar, 2, 0, 3))
        block2.apply(calendar, 2, 0, 3)
        calendar.dump()
        assertTrue(block3.canApply(calendar, 3, 0, 1))
        block3.apply(calendar, 3, 0, 1)
        calendar.dump()
    }

    @Test
    fun rotateSimpleBlocks() {
        val calendar = CalendarPuzzleField();

        val r0 = """
                    OX
                    OX
                    XX
                """.trimIndent()
        val r1 = """
            XXX
            OOX
        """.trimIndent()

        var r2 = """
            XX
            XO
            XO
        """.trimIndent()

        var r3 = """
            XOO
            XXX
        """.trimIndent()

        var r4 = """
            XO
            XO
            XX
        """.trimIndent()

        var r5 = """
            OOX
            XXX
        """.trimIndent()

        var r6 = """
            XX
            OX
            OX
        """.trimIndent()
        var r7 = """
            XXX
            XOO
        """.trimIndent()


        val block = CalendarBlockFactory.createFromText(
                """
                    OOX
                    XXX
                """.trimIndent(), 1
        );

        assertEquals(block.toString(1), r1)
        assertEquals(block.toString(2), r2)
        assertEquals(block.toString(3), r3)
        assertEquals(block.toString(4), r4)
        assertEquals(block.toString(5), r5)
        assertEquals(block.toString(6), r6)
        assertEquals(block.toString(7), r7)
        assertEquals(block.toString(0), r0)
    }

    @Test
    fun fillFirstRow() {
        val calendar = CalendarPuzzleField();
        val block1 = CalendarBlockFactory.createFromText(
                """
                    XX
                    XO
                    XO
                """.trimIndent(), 1
        );

        val block2 = CalendarBlockFactory.createFromText(
                """
                    OXO
                    XXX
                """.trimIndent(), 2
        );

        val block3 = CalendarBlockFactory.createFromText(
                """
                    XX
                    OX
                    XX 
                """.trimIndent(), 3
        );

        val block4 = CalendarBlockFactory.createFromText(
                """
                    OOOX
                    OOOX
                    OOOX
                    XXXX 
                """.trimIndent(), 4
        );

        calendar.dump()
        assertNotNull(calendar.putBlock(0, block1, 0))
        assertNotNull(calendar.putBlock(0, block2, 0))
        assertNotNull(calendar.putBlock(0, block3, 0))
        assertNotNull(calendar.putBlock(0, block4, 0))
        calendar.dump()
        assertTrue(calendar.isRowDone(0))
        assertFalse(calendar.isRowDone(2))
        assertNotNull(calendar.putBlock(2, block4, 1))
        calendar.dump()
    }

    @Test
    fun testDailyField() {
        DailyCalendarPuzzleField().dump()
    }
}