import utils.readInput
import kotlin.collections.map

class Day5 (
    input: String,
) {

    var ranges = mutableListOf<LongRange>()
    var ids = listOf<Long>()

    init {
        val (rangeInput, idInput) = input.trim().split("\n\n")

        ranges = rangeInput.lines().map { line ->
            val (a, b) = line.split("-").map { it.toLong() }
            a..b
        }
        .sortedWith(compareBy<LongRange> { it.first }.thenBy { it.last })
        .toMutableList()

        val merged = mutableListOf<LongRange>()
        var current = ranges.first()

        for (range in ranges.drop(1)) {
            if (range.first <= current.last) {
                current = current.first..maxOf(current.last, range.last)
            } else {
                merged += current
                current = range
            }
        }

        merged += current
        ranges = merged

        ids = idInput.lines().map { it.toLong() }
    }

    fun partOne(): Any {
        var fresh = 0
        for (id in ids) {
            for (range in ranges) {
                if (range.contains(id)) {
                    fresh++
                    break
                }
            }
        }
        return fresh
    }

    fun partTwo(): Any {
        return ranges.sumOf { it.last - it.first + 1 }
    }
}

fun main() {
    val input = readInput("day05.txt")
    val sol = Day5(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}