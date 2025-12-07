object Day05 {

    fun parseRange(s: String) = s.split('-').map { it.toLong() }.let { it.first()..it.last() }

    fun part1(lines: List<String>): Int {
        val ranges = lines.takeWhile { it.isNotEmpty() }
            .map { parseRange(it) }

        return lines.dropWhile { it.isNotEmpty() }
            .drop(1)
            .map { it.toLong() }
            .count { id -> ranges.any { id in it } }
    }

    fun mergeRanges(a: LongRange, b: LongRange): List<LongRange> {
        return if (a.last + 1 < b.first) listOf(a, b)
        else if (a.last < b.last) listOf(a.first..b.last)
        else listOf(a)
    }

    fun part2(lines: List<String>): Long {
        val ranges = lines.takeWhile { it.isNotEmpty() }
            .map { parseRange(it) }
            .sortedBy { it.first }

        val nonOverlapping = ranges.drop(1)
            .fold(listOf(ranges.first())) { acc, range ->
                acc.dropLast(1) + mergeRanges(acc.last(), range)
            }

        return nonOverlapping.sumOf { 1 + it.last - it.first }
    }

    fun main() {
        val testInput =
            """
                3-5
                10-14
                16-20
                12-18

                1
                5
                8
                11
                17
                32
        """.trimIndent().lines()
        require(part1(testInput) == 3)
        require(part2(testInput) == 14L)

        val realInput = inputOfDay(5)
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day05.main()