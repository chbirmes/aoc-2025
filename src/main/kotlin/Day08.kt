import kotlin.math.pow
import kotlin.math.sqrt

object Day08 {

    data class JunctionBox(val x: Int, val y: Int, val z: Int) {

        private fun Int.squared() = toDouble().pow(2)

        fun distanceTo(other: JunctionBox) =
            sqrt((x - other.x).squared() + (y - other.y).squared() + (z - other.z).squared())

        companion object {
            fun parse(s: String) =
                s.split(',')
                    .map { it.toInt() }
                    .let { JunctionBox(it[0], it[1], it[2]) }
        }
    }

    private fun calculateDistances(boxes: List<JunctionBox>) =
        boxes.flatMapIndexed { index, box ->
            boxes.drop(index + 1).map { otherBox ->
                (box to otherBox) to box.distanceTo(otherBox)
            }
        }

    private fun MutableList<MutableSet<JunctionBox>>.connect(boxA: JunctionBox, boxB: JunctionBox) {
        val circuitA = first { boxA in it }
        val circuitB = first { boxB in it }
        if (circuitA != circuitB) {
            circuitA.addAll(circuitB)
            remove(circuitB)
        }
    }

    fun part1(lines: List<String>, connections: Int): Int {
        val boxes = lines.map { JunctionBox.parse(it) }
        val distances = calculateDistances(boxes)
        val circuits = boxes.map { mutableSetOf(it) }.toMutableList()

        distances.sortedBy { it.second }
            .take(connections)
            .map { it.first }
            .forEach { (boxA, boxB) -> circuits.connect(boxA, boxB) }

        return circuits.map { it.size }
            .sorted()
            .takeLast(3)
            .fold(1, Int::times)
    }

    fun part2(lines: List<String>): Long {
        val boxes = lines.map { JunctionBox.parse(it) }
        val distances = calculateDistances(boxes)
        val circuits = boxes.map { mutableSetOf(it) }.toMutableList()

        distances.sortedBy { it.second }
            .map { it.first }
            .forEach { (boxA, boxB) ->
                circuits.connect(boxA, boxB)
                if (circuits.size == 1) return boxA.x.toLong() * boxB.x.toLong()
            }
        error("should have connected all boxes")
    }

    fun main() {
        val testInput =
            """
                162,817,812
                57,618,57
                906,360,560
                592,479,940
                352,342,300
                466,668,158
                542,29,236
                431,825,988
                739,650,466
                52,470,668
                216,146,977
                819,987,18
                117,168,530
                805,96,715
                346,949,466
                970,615,88
                941,993,340
                862,61,35
                984,92,344
                425,690,689
        """.trimIndent().lines()
        require(part1(testInput, 10) == 40)
        require(part2(testInput) == 25272L)

        val realInput = inputOfDay(8)
        println(part1(realInput, 1000))
        println(part2(realInput))
    }
}

fun main() = Day08.main()