import D2.neighbors

object Day04 {

    fun D2.Position.isAccessible(papers: Set<D2.Position>) =
        neighbors(includeDiagonal = true).count { it in papers } < 4

    fun part1(lines: List<String>): Int {
        val grid = D2.Grid(lines)
        val papers = grid.filter('@').toSet()
        return papers.count { it.isAccessible(papers) }
    }

    tailrec fun removePaper(papers: Set<D2.Position>): Set<D2.Position> {
        val removable = papers.filter { it.isAccessible(papers) }
        return if (removable.isEmpty()) papers
        else removePaper(papers - removable.toSet())
    }

    fun part2(lines: List<String>): Int {
        val grid = D2.Grid(lines)
        val papers = grid.filter('@').toSet()
        return papers.size - removePaper(papers).size
    }

    fun main() {
        val testInput =
            """
                ..@@.@@@@.
                @@@.@.@.@@
                @@@@@.@.@@
                @.@@@@..@.
                @@.@@@@.@@
                .@@@@@@@.@
                .@.@.@.@@@
                @.@@@.@@@@
                .@@@@@@@@.
                @.@.@@@.@.
        """.trimIndent().lines()
        require(part1(testInput) == 13)
        require(part2(testInput) == 43)

        val realInput = inputOfDay(4)
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day04.main()