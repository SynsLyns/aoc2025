import utils.readInput
import kotlin.collections.map

class Day7 (
    input: String,
) {
    val grid = input
        .lines()
        .map { line ->
            line.map { char -> char }.toMutableList()
        }.toMutableList()

    fun partOne(): Any {
        var splits = 0
        val beams = mutableSetOf<Int>()

        for (r in grid.indices) {
            for (c in grid[r].indices) {
                when (grid[r][c]) {
                    'S' -> beams.add(c)
                    '^' -> {
                        if (beams.contains(c)) {
                            beams.remove(c)
                            beams.addAll(listOf(c + 1, c - 1))
                            splits++
                        }
                    }
                }
            }
        }

        return splits
    }

    fun partTwo(): Any {
        val beams = mutableMapOf<Int, Long>()

        for (r in grid.indices) {
            for (c in grid[r].indices) {
                when (grid[r][c]) {
                    'S' -> beams[c] = 1
                    '^' -> {
                        if (c in beams) {
                            val splits = beams.remove(c)!!
                            beams[c - 1] = (beams[c - 1] ?: 0) + splits
                            beams[c + 1] = (beams[c + 1] ?: 0) + splits
                        }
                    }
                }
            }
        }

        return beams.values.sum()
    }
}

fun main() {
    val input = readInput("day07.txt")
    val sol = Day7(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}