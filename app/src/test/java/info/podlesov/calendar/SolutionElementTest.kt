package info.podlesov.calendar

import org.junit.Assert.assertTrue
import org.junit.Test

class SolutionElementTest {
    @Test
    fun fillFirstRow() {
        val solution = SolutionElement()
        var childs: List<SolutionElement> = IntRange(0, solution.availableBlocks.size - 1)
                .mapNotNull { solution.addBlock(solution.availableBlocks[it]) }.flatten()

        assertTrue(childs.isNotEmpty())

        childs = childs
                .map { IntRange(0, it.availableBlocks.size - 1)
                .mapNotNull { i -> it.addBlock(it.availableBlocks[i]) } }
                .flatten()
                .flatten();

        childs = childs
                .map { IntRange(0, it.availableBlocks.size - 1)
                .mapNotNull { i -> it.addBlock(it.availableBlocks[i]) } }
                .flatten()
                .flatten();

        childs = childs
                .map { IntRange(0, it.availableBlocks.size - 1)
                .mapNotNull { i -> it.addBlock(it.availableBlocks[i]) } }
                .flatten()
                .flatten();

        childs.filter { it.field.isRowDone(0) }
                .forEach() {
                    it.field.dump()
                    it.availableBlocks.forEach() {
                        println()
                        println(it.toString(0))
                        println()
                    }
                }
        assertTrue(childs.isNotEmpty())
    }

    @Test
    fun fillEntireField() {
        val child = SolutionElement.findTodaySolution()

        child.field.dump()

        for (i in 0 until child.field.field[0].size-1) {
            assertTrue(child.field.isRowDone(i))
        }
    }
}