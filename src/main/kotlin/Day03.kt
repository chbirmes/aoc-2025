import kotlin.math.pow

object Day03 {

    fun maxNumber(remaining: String, digits: Int): Long {
        return if (digits == 1) {
            remaining.maxOf { it.digitToInt() }.toLong()
        } else {
            val head = remaining.dropLast(digits - 1)
            val firstMax = head.map { it.digitToInt() }
                .withIndex()
                .maxWith(
                    Comparator.comparingInt(IndexedValue<Int>::value)
                        .then(Comparator.comparingInt(IndexedValue<Int>::index).reversed())
                )
            firstMax.value * 10.0.pow(digits - 1).toLong() +
                    maxNumber(remaining.substring(firstMax.index + 1), digits - 1)
        }
    }

    fun part1(lines: List<String>): Long = lines.sumOf { maxNumber(it, 2) }

    fun part2(lines: List<String>): Long = lines.sumOf { maxNumber(it, 12) }

    fun main() {
        val testInput =
            """
                987654321111111
                811111111111119
                234234234234278
                818181911112111
        """.trimIndent().lines()
        require(part1(testInput) == 357L)
        require(part2(testInput) == 3121910778619)

        val realInput = inputOfDay(3)
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day03.main()