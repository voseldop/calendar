package info.podlesov.calendar

class SolutionElement {
    val field: CalendarPuzzleField
    val log: List<Pair<CalendarBlock, Triple<Int, Int, Int>>>
    val availableBlocks: List<CalendarBlock>

    constructor(field: CalendarPuzzleField = CalendarPuzzleField()) {
        this.field = field
        log = listOf()
        availableBlocks = CalendarBlockFactory.allBlocks();
    }

    private constructor(parent: SolutionElement, field: CalendarPuzzleField, block: CalendarBlock, x: Int, y: Int, rotation: Int) {
        this.field = field
        log = parent.log.plus(block to Triple(x, y,rotation))
        availableBlocks = parent.availableBlocks.minus(block)
    }

    fun addBlock(block: CalendarBlock): List<SolutionElement> = IntRange(0, 7)
            .mapNotNull {
                val newField = CalendarPuzzleField(field)
                var row = 0;
                while (row < field.field[0].size) {
                    if (field.isRowDone(row)) {
                        row++;
                    } else {
                        break;
                    }
                }
                newField.putBlock(row, block, it)?.let {
                    SolutionElement(this, newField, block, it.first, it.second, it.third)
                }
            }.toList()


    companion object {
        fun findTodaySolution(): SolutionElement {
            val solution = SolutionElement(DailyCalendarPuzzleField())
            var childs: MutableList<SolutionElement> = IntRange(0, solution.availableBlocks.size - 1)
                    .mapNotNull { solution.addBlock(solution.availableBlocks[it]) }.flatten().toMutableList()

            while (childs[0].availableBlocks.isNotEmpty()) {
                var child = childs[0]
                childs.removeAt(0)

                var newChilds = IntRange(0, child.availableBlocks.size - 1)
                         .mapNotNull { i -> child.addBlock(child.availableBlocks[i]) }.flatten()
                childs.addAll(0, newChilds)
            }

            return childs[0]
        }
    }
}