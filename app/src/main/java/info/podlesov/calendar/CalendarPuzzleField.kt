package info.podlesov.calendar

open class CalendarPuzzleField {
    val width = 7
    val height = 7
    val field: Array<Array<Byte>>
    val blockList: MutableList<CalendarBlock> = mutableListOf()

    constructor() {
        field = Array(7, { Array(8, { 0.toByte() }) })
        field[6][0] = 9;
        field[6][1] = 9;
        field[3][6] = 9;
        field[4][6] = 9;
        field[5][6] = 9;
        field[6][6] = 9;
    }

    constructor(origin: CalendarPuzzleField) {
        field = origin.field.map { it.clone() }.toTypedArray()
    }

    fun isRowDone(row: Int): Boolean {
        for (i in field.indices) {
            if (field[i][row] == 0.toByte()) {
                return false;
            }
        }
        return true;
    }

    fun putBlock(row: Int, block: CalendarBlock, rotation: Int): Triple<Int, Int, Int>? {
        var free = field.size;
        for (i in field.indices) {
            if (field[i][row] == 0.toByte()) {
                free = i;
                break;
            }
        }

        if (free == field.size) {
            return null;
        }

        if (!block.canApply(this, free, row, rotation)) {
            return null;
        }

        blockList.add(block);
        block.apply(this, free, row, rotation);
        return Triple(free, row, rotation);
    }

    fun dump() {
        println("----------------");
        for (y in  0..6) {
            var row = StringBuilder()
            for (x in 0 ..6) {
                row.append(field[x][y])
            }
            println(row);
        }
        println("----------------");
    }
}