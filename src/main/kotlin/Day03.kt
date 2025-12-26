import utils.readInput
import kotlin.collections.map
import kotlin.math.max

class Day3 (
    input: String,
) {
    var banks: List<List<Int>> = mutableListOf()

    init {
        banks = input
            .lines()
            .filter { it.isNotBlank() }
            .map { line ->
                line.map { it.digitToInt() }
            }
    }

    fun partOne(): Any {
        return banks.sumOf { bank ->
            var best = 0
            var l = 0

            for (r in 1 until bank.size) {
                best = max(best, bank[l]*10 + bank[r])
                if (bank[r] > bank[l]) l = r
            }

            best
        }
    }

    fun partTwo(): Any {
        return banks.sumOf { bank ->
            val best = MutableList(12) { 0 }

            var start = 0
            for (i in 0 until 12) {
                for (r in start until bank.size - 11 + i) {
                    if (bank[r] > best[i]) {
                        best[i] = bank[r]
                        start = r + 1
                    }
                }
            }

            best.joinToString("").toLong()
        }
    }
}

fun main() {
    val input = readInput("day03.txt")
    val sol = Day3(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}