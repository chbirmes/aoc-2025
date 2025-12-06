object Day06 {

    fun parseNumbers(s: String) =
        s.split(Regex("\\s+"))
            .filter { it.isNotBlank() }
            .map { it.toLong() }

    fun parseOperators(s: String): List<(Long, Long) -> Long> =
        s.split(Regex("\\s+"))
            .filter { it.isNotBlank() }
            .map {
                when (it) {
                    "+" -> Long::plus
                    "*" -> Long::times
                    else -> throw IllegalArgumentException()
                }
            }

    fun part1(lines: List<String>): Long {
        val numberLists = lines.dropLast(1)
            .map { parseNumbers(it) }
        val operators = parseOperators(lines.last())
        return numberLists.first().indices.sumOf { i ->
            numberLists.drop(1)
                .fold(numberLists.first()[i]) { acc, list -> operators[i](acc, list[i]) }
        }
    }

    fun <T> transpose(lists: List<List<T>>): List<List<T>> {
        return lists.first().indices.map { i ->
            lists.map { it[i] }
        }
    }

    fun part2(lines: List<String>): Long {
        val transposed = transpose(lines.dropLast(1).map { it.toCharArray().asList() })
            .map { it.joinToString(separator = "").trim() }

        val emptyIndices = buildList {
            add(-1)
            addAll(transposed.withIndex().filter { it.value.isEmpty() }.map { it.index })
            add(transposed.size)
        }

        val problems = emptyIndices.windowed(2)
            .map {
                transposed.subList(it[0] + 1, it[1])
                    .map { s -> s.toLong() }
            }

        val operators = parseOperators(lines.last())

        return problems.withIndex().sumOf { (i, problem) ->
            problem.drop(1)
                .fold(problem.first()) { acc, s -> operators[i](acc, s) }

        }
    }

    fun main() {
        val testInput =
            """
               123 328  51 64 
                45 64  387 23 
                 6 98  215 314
               *   +   *   +  
        """.trimIndent().lines()
        require(part1(testInput) == 4277556L)
        require(part2(testInput) == 3263827L)

        val realInput = inputOfDay(6)
        println(part1(realInput))
        println(part2(realInput))
    }
}

fun main() = Day06.main()