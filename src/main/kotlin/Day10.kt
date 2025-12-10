object Day10 {

    class Machine1(val target: Int, val buttons: List<Int>) {

        fun applyButtons(value: Int) = buttons.map { value xor it }

        fun solve(): Int {
            val seed = setOf(0) to setOf(0)
            return generateSequence(seed) { (current, seen) ->
                val newCurrent = current.flatMap { applyButtons(it) }
                    .filterNot { it in seen }
                    .toSet()
                newCurrent to (seen + newCurrent)
            }
                .indexOfFirst { (current, _) -> target in current }
        }

        companion object {
            fun parse(s: String): Machine1 {
                val parts = s.split(' ')

                val lights = parts.first()
                    .drop(1)
                    .dropLast(1)

                val target = lights.replace('.', '0')
                    .replace('#', '1')
                    .toInt(2)

                val buttons = parts.drop(1)
                    .dropLast(1)
                    .map { button ->
                        button.drop(1)
                            .dropLast(1)
                            .split(',')
                            .map { it.toInt() }
                            .fold(0) { acc, i -> acc or (1 shl (lights.length - (i + 1))) }
                    }

                return Machine1(target, buttons)
            }
        }
    }

    fun part1(lines: List<String>): Int =
        lines.map { Machine1.parse(it) }
            .sumOf { it.solve() }

    fun part2(lines: List<String>): Int =
        TODO()


    fun main() {
        val testInput =
            """
                [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
        """.trimIndent().lines()
        require(part1(testInput) == 7)
//        require(part2(testInput) == 33)

        val realInput = inputOfDay(10)
        println(part1(realInput))
//        println(part2(realInput))
    }
}

fun main() = Day10.main()