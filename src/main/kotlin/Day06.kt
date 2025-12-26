import utils.readInput
import kotlin.collections.map

class Day6 (
    private val input: String,
) {
    private val operators = input
        .lineSequence()
        .last().trim()
        .split("\\s+".toRegex())

    fun partOne(): Any {
        val results = input
            .lineSequence()
            .first().trim()
            .split("\\s+".toRegex())
            .map { it.toLong() }
            .toMutableList()

        val lines = input
            .lines()
            .drop(1)
            .dropLast(1)
            .map { line ->
                line.trim().split("\\s+".toRegex()).map { it.toLong() }
            }

        lines.forEach { line ->
            results.indices.forEach { i ->
                when (operators[i]) {
                    "*" -> results[i] *= line[i]
                    "+" -> results[i] += line[i]
                }
            }
        }

        return results.sum()
    }

    fun partTwo(): Any {
        val rows = input.lines().dropLast(1)
        val cols = rows.first().length
        val nums = List(cols) { i ->
            rows.map { it[i] }
                .filter { it != ' ' }
                .joinToString("")
                .takeIf { it.isNotEmpty() }
                ?.toInt() ?: -1
        }

        var result = 0L
        var problem = 0
        var current = if (operators[problem] == "*") 1L else 0L

        nums.forEach { num ->
            if (num == -1) {
                result += current
                problem++
                current = if (operators[problem] == "*") 1L else 0L
            } else {
                when (operators[problem]) {
                    "*" -> current *= num
                    "+" -> current += num
                }
            }
        }

        return result + current
    }
}

fun main() {
    val input = readInput("day06.txt")
    val sol = Day6(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}