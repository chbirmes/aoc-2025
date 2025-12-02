import kotlin.math.sign

object Day01 {

    enum class Direction(val sign: Int) {
        LEFT(-1),
        RIGHT(1);

        companion object {
            fun parse(c: Char) = if (c == 'L') LEFT else RIGHT
        }

    }

    data class Rotation(val direction: Direction, val distance: Int) {
        companion object {
            fun parse(s: String) = Rotation(Direction.parse(s.first()), s.drop(1).toInt())
        }
    }

    fun rotate(position: Int, rotation: Rotation) =
        (position + (rotation.direction.sign * rotation.distance)).mod(100)

    data class PositionAndZeroCount(val position: Int, val zeroCount: Int) {
        fun rotate(rotation: Rotation): PositionAndZeroCount {
            val newPosition = rotate(position, rotation)
            val newZeroCount = (position + (rotation.direction.sign * rotation.distance))
                .let { if (it > 0) it / 100 else -it / 100 + position.sign }
            return PositionAndZeroCount(newPosition, zeroCount + newZeroCount)
        }
    }

    fun part1(lines: List<String>) =
        lines.map(Rotation::parse)
            .runningFold(50, ::rotate)
            .count { it == 0 }

    fun part2(lines: List<String>): Int =
        lines.map(Rotation::parse)
            .fold(PositionAndZeroCount(50, 0), PositionAndZeroCount::rotate)
            .zeroCount

    fun main() {
        val testInput =
            """
                L68
                L30
                R48
                L5
                R60
                L55
                L1
                L99
                R14
                L82
        """.trimIndent().lines()
        require(part1(testInput) == 3)
        require(part2(testInput) == 6)

        val realInput = inputOfDay(1)
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day01.main()