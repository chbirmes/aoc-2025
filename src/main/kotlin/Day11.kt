object Day11 {

    typealias Connection = Pair<String, String>

    fun parseConnections(s: String): List<Connection> {
        val start = s.take(3)
        return s.drop(5)
            .split(' ')
            .map { start to it }
    }

    fun List<Connection>.waysFromTo(
        from: String,
        to: String,
        without: String? = null,
        cache: MutableMap<String, Long> = mutableMapOf()
    ): Long {
        return if (cache.containsKey(to)) return cache[to]!!
        else if (to == from) 1
        else filter { it.second == to && it.first != without }
            .sumOf { waysFromTo(from, it.first, without, cache) }
            .also { cache[to] = it }
    }


    fun part1(lines: List<String>): Long {
        val connections = lines.flatMap { parseConnections(it) }
        return connections.waysFromTo("you", "out")
    }

    fun part2(lines: List<String>): Long {
        val connections = lines.flatMap { parseConnections(it) }
        return connections.run {
            waysFromTo("svr", "dac", "fft") *
                    waysFromTo("dac", "fft") *
                    waysFromTo("fft", "out") +
                    waysFromTo("fft", "dac") *
                    waysFromTo("svr", "fft", "dac") *
                    waysFromTo("dac", "out")
        }
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