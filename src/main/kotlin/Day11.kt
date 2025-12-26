import utils.readInput

class Day11(
    input: String,
) {
    val serverMap = input
        .lines()
        .associate { line ->
            val (key, values) = line.split(": ")
            key to values.split(" ")
        }

    fun solve(
        start: String,
        end: String,
        ignoreList: List<String> = emptyList(),
    ): Long {
        val memo = mutableMapOf<String, Long>()
        fun backtrack(cur: String): Long {
            if (cur == end) return 1
            memo[cur]?.let { return it }

            var count = 0L
            for (server in serverMap[cur].orEmpty()) {
                if (server !in ignoreList) count += backtrack(server)
            }

            memo[cur] = count
            return count
        }

        return backtrack(start)
    }

    fun partOne(): Any {
        return solve("you", "out")
    }

    fun partTwo(): Any {
        val routesA = solve("svr", "fft", listOf("dac")) *
                solve("fft", "dac") *
                solve("dac", "out")
        val routesB = solve("svr", "dac", listOf("fft")) *
                solve("dac", "fft") *
                solve("fft", "out")
        return routesA + routesB
    }
}

fun main() {
    val input = readInput("day11.txt")
    val sol = Day11(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}