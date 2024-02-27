package info.podlesov.calendar

open class SimpleCalendarBlock(val id: Byte = 1, val configuration: Array<Array<Boolean>> = Array(3, { Array(2, { true }) })): CalendarBlock {
    val mutation: MutableMap<Int, Array<Array<Boolean>>> = HashMap()

    private fun getRotated(rotation: Int): Array<Array<Boolean>> {
        if (mutation.containsKey(rotation)) {
            return mutation.get(rotation)!!
        } else {
            var mask = configuration

            if (rotation > 3) {
                mask = mirror(mask)
            }

            for (i in 0 until rotation % 4) {
                mask = rotate(mask)
            }
            mutation.put(rotation, mask)
            return mask
        }
    }

    override fun canApply(field: CalendarPuzzleField, x0: Int, y0: Int, rotation: Int): Boolean {
        var mask = getRotated(rotation)

        val height = mask[0].size
        val width = mask.size
        val x0adjust = adjust(mask)

        if (x0 + x0adjust + width > field.width || x0+x0adjust < 0) {
            return false
        }

        if (y0 + height > field.height) {
            return false
        }

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (mask[x][y].and(field.field[x+x0+x0adjust][y+y0]!=0.toByte()))
                    return false;
            }
        }
        return true;
    }

    override fun apply(field: CalendarPuzzleField, x0: Int, y0: Int, rotation: Int) {
        var mask = getRotated(rotation)

        val height = mask[0].size
        val width = mask.size
        val x0adjust = adjust(mask)

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (mask[x][y]) {
                    field.field[x+x0+x0adjust][y+y0] = id;
                }
            }
        }
    }

    override fun undo(field: CalendarPuzzleField, x0: Int, y0: Int, rotation: Int) {
        var mask = configuration

        if (rotation > 3) {
            mask = mirror(mask)
        }

        for (i in 0 until rotation % 4) {
            mask = rotate(mask)
        }

        val height = mask[0].size
        val width = mask.size
        val x0adjust = adjust(mask)

        for (y in 0 until height) {
            for (x in 0 until width) {
                if (mask[x][y]) {
                    field.field[x+x0+x0adjust][y+y0] = 0;
                }
            }
        }
    }

    fun rotate(before: Array<Array<Boolean>>): Array<Array<Boolean>> {
        val width = before.size
        val height = before[0].size
        val field = Array(height, { Array(width, {false}) })
        for (x in 0 until width) {
            for (y in 0 until height) {
                field[y][x] = before[x][height - y - 1]
            }
        }
        return field
    }

    fun mirror(before: Array<Array<Boolean>>): Array<Array<Boolean>> {
        val width = before.size
        val height = before[0].size
        val field = Array(width, { Array(height, {false}) })
        for (x in 0 until width) {
            for (y in 0 until height) {
                field[x][y] = before[x][height - y - 1]
            }
        }
        return field
    }

    fun adjust(mask: Array<Array<Boolean>>): Int {
        var offset = 0;
        for (x in 0 until mask.size) {
            if (!mask[x][0]) {
                offset--;
            } else {
                break;
            }

        }
        return offset
    }

    override fun toString(rotation: Int): String {
        var mask = configuration

        if (rotation > 3) {
            mask = mirror(mask)
        }

        for (i in 0 until rotation % 4) {
            mask = rotate(mask)
        }
        return mask.joinToString(separator = "\n") {  line -> line.joinToString(separator = "") { if (it) "X" else "O" } }
    }
}