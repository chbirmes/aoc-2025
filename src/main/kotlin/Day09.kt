import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

object Day09 {

    fun range(x: Int, y: Int) = if (x < y) x..y else y..x

    data class Rectangle(val a: D2.Position, val b: D2.Position) {

        fun isInside(shape: Shape): Boolean {
            // we neglect flat Rectangles, so we use the point right under the left corner to check whether it is inside
            val inRectangle = D2.Position(min(a.x, b.x) + 1, min(a.y, b.y) + 1)
            // horizonal ray to the left for counting inside/outside transitions
            val ray = shape.minX..inRectangle.x

            val intersectionVerticals = shape.lines
                .asSequence()
                .filter { it.direction.x == 0 }
                .filter { it.start.x in ray }
                .filter { inRectangle.y in range(it.start.y, it.end.y) }

            val intersectionHorizontals = shape.lines
                .asSequence()
                .filter { it.direction.y == 0 }
                .filter { it.start.y == inRectangle.y }
                .filter { it.start.x < inRectangle.x && it.end.x < inRectangle.x }
                .filter { shape.hasAlternatingCorners(it) }
            return (intersectionVerticals.count() + intersectionHorizontals.count()) % 2 != 0
        }

        fun containsOtherLines(shape: Shape): Boolean {
            val insideX = (min(a.x, b.x) + 1)..<max(a.x, b.x)
            val insideY = (min(a.y, b.y) + 1)..<max(a.y, b.y)
            return shape.lines.any { line ->
                if (line.direction.x == 0) {
                    val yRange = range(line.start.y, line.end.y)
                    line.start.x in insideX && !(yRange.last < insideY.first || insideY.last < yRange.first)
                } else {
                    val xRange = range(line.start.x, line.end.x)
                    line.start.y in insideY && !(xRange.last < insideX.first || insideX.last < xRange.first)
                }
            }
        }

        val area: Long
            get() = (abs(a.x - b.x) + 1).toLong() * (abs(a.y - b.y) + 1).toLong()
    }

    fun parseTile(s: String) =
        s.split(',')
            .map { it.toInt() }
            .let { D2.Position(it.first(), it.last()) }

    fun rectanglesOf(tiles: List<D2.Position>): List<Rectangle> = tiles.flatMapIndexed { index, tile ->
        tiles.drop(index + 1).map { Rectangle(tile, it) }
    }


    fun part1(lines: List<String>): Long {
        val tiles = lines.map { parseTile(it) }
        val rectangles = rectanglesOf(tiles)
        return rectangles.maxOf { it.area }
    }

    data class Line(val start: D2.Position, val end: D2.Position) {
        val direction: D2.Position
            get() = D2.Position((end.x - start.x).sign, (end.y - start.y).sign)
    }

    data class Shape(val lines: List<Line>) {
        fun hasAlternatingCorners(line: Line): Boolean {
            val i = lines.indexOf(line)
            val prev = if (i == 0) lines.last() else lines[i - 1]
            val next = if (i == lines.indices.last) lines.first() else lines[i + 1]
            val prevRight = prev.direction.turnRight() == line.direction
            val nextRight = line.direction.turnRight() == next.direction
            return prevRight != nextRight
        }

        val minX: Int
            get() = lines.minOf { min(it.start.x, it.end.x) }
    }

    fun part2(lines: List<String>): Long {
        val tiles = lines.map { parseTile(it) }
        val lines = tiles.windowed(2)
            .map { Line(it.first(), it.last()) } +
                Line(tiles.last(), tiles.first())
        val shape = Shape(lines)
        val rectangles = rectanglesOf(tiles)
            .filter { abs(it.b.x - it.a.x) > 1 && abs(it.b.y - it.a.y) > 1 } // neglect flat rectangles
            .filter { it.isInside(shape) }
            .filterNot { it.containsOtherLines(shape) }
            .toList()

        return rectangles.maxOf { it.area }
    }


    fun main() {
        val testInput =
            """
                7,1
                11,1
                11,7
                9,7
                9,5
                2,5
                2,3
                7,3
        """.trimIndent().lines()
        require(part1(testInput) == 50L)
        require(part2(testInput) == 24L)

        val realInput = inputOfDay(9)
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day09.main()