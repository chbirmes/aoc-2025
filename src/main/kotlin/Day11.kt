object Day11 {

    typealias Connection = Pair<String, String>

    fun parseConnections(s: String): List<Connection> {
        val start = s.take(3)
        return s.drop(5)
            .split(' ')
            .map { start to it }
    }

    fun List<Connection>.waysTo(goal: String, cache: MutableMap<String, Long> = mutableMapOf()): Long {
        return if (cache.containsKey(goal)) return cache[goal]!!
        else if (goal == "you") 1
        else filter { it.second == goal }
            .sumOf { waysTo(it.first, cache) }
            .also { cache[goal] = it }
    }

    fun List<Connection>.waysPart2(): Long {
        return waysFromTo("svr", "dac", setOf("fft")) *
                waysFromTo("dac", "fft") *
                waysFromTo("fft", "out") +
                waysFromTo("fft", "dac") *
                waysFromTo("svr", "fft", setOf("dac")) *
                waysFromTo("dac", "out")

    }

    fun List<Connection>.waysFromTo(
        from: String,
        to: String,
        without: Set<String> = emptySet(),
        cache: MutableMap<String, Long> = mutableMapOf()
    ): Long {
        return if (cache.containsKey(to)) return cache[to]!!
        else if (to == from) 1
        else filter { it.second == to && it.first !in without }
            .sumOf { waysFromTo(from, it.first, without + it.first, cache) }
            .also { cache[to] = it }
    }


    fun part1(lines: List<String>): Long {
        val connections = lines.flatMap { parseConnections(it) }
        return connections.waysTo("out")
    }

    fun part2(lines: List<String>): Long {
        val connections = lines.flatMap { parseConnections(it) }
        return connections.waysPart2()
    }

    fun main() {
        val testInput1 = """
                aaa: you hhh
                you: bbb ccc
                bbb: ddd eee
                ccc: ddd eee fff
                ddd: ggg
                eee: out
                fff: out
                ggg: out
                hhh: ccc fff iii
                iii: out
        """.trimIndent().lines()

        val testInput2 = """
            svr: aaa bbb
            aaa: fft
            fft: ccc
            bbb: tty
            tty: ccc
            ccc: ddd eee
            ddd: hub
            hub: fff
            eee: dac
            dac: fff
            fff: ggg hhh
            ggg: out
            hhh: out
        """.trimIndent().lines()

        require(part1(testInput1) == 5L)
        require(part2(testInput2) == 2L)

        val realInput = inputOfDay(11)
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day11.main()