object Day07 {

    val waysCache = mutableMapOf<D2.Position, Long>()

    fun D2.Grid.waysTo(position: D2.Position): Long {
        if (waysCache.containsKey(position)) return waysCache[position]!!
        if (this[position] == 'S') return 1L.also { waysCache[position] = it }

        var ways = 0L

        val above = position + D2.up
        if (above in this && this[above] != '^') ways += waysTo(above)

        val left = position + D2.left
        if (left in this && this[left] == '^') ways += waysTo(left + D2.up)

        val right = position + D2.right
        if (right in this && this[right] == '^') ways += waysTo(right + D2.up)

        return ways.also { waysCache[position] = it }
    }

    fun part1(lines: List<String>): Int {
        waysCache.clear()
        val grid = D2.Grid(lines)
        val splitters = grid.filter('^')
        return splitters.count { grid.waysTo(it) > 0 }
    }

    fun part2(lines: List<String>): Long {
        waysCache.clear()
        val grid = D2.Grid(lines)
        return grid.positions()
            .filter { it.y == grid.maxY }
            .sumOf { grid.waysTo(it) }
    }

    fun main() {
        val testInput =
            """
                .......S.......
                ...............
                .......^.......
                ...............
                ......^.^......
                ...............
                .....^.^.^.....
                ...............
                ....^.^...^....
                ...............
                ...^.^...^.^...
                ...............
                ..^...^.....^..
                ...............
                .^.^.^.^.^...^.
                ...............
        """.trimIndent().lines()
        require(part1(testInput) == 21)
        require(part2(testInput) == 40L)

        val realInput = inputOfDay(7)
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day07.main()