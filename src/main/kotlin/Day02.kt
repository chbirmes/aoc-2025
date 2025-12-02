object Day02 {

    fun parseRanges(s: String) =
        s.split(',')
            .map { range -> range.split('-').let { it.first().toLong()..it.last().toLong() } }

    fun String.isTwiceRepeatedSequence() = length % 2 == 0 && take(length / 2) == drop(length / 2)

    fun part1(line: String): Long =
        parseRanges(line)
            .sumOf { range -> range.filter { it.toString().isTwiceRepeatedSequence() }.sum() }

    fun divisors(i: Int) =
        buildList {
            addAll((2..i / 2).filter { i % it == 0 })
            add(i)
        }

    fun String.consistsOfRepetitions() =
        length > 1 &&
                divisors(length).any { divisor ->
                    chunked(length / divisor).let { chunks ->
                        chunks.drop(1).all { it == chunks.first() }
                    }
                }

    fun part2(line: String): Long =
        parseRanges(line)
            .sumOf { range -> range.filter { it.toString().consistsOfRepetitions() }.sum() }

    fun main() {
        val testInput =
            """
                11-22,95-115,998-1012,1188511880-1188511890,222220-222224,
                1698522-1698528,446443-446449,38593856-38593862,565653-565659,
                824824821-824824827,2121212118-2121212124
        """.trimIndent().lines().joinToString(separator = "")
        require(part1(testInput) == 1227775554L)
        require(part2(testInput) == 4174379265L)

        val realInput = inputOfDay(2).first()
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day02.main()