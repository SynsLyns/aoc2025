import utils.readInput
import kotlin.collections.map

class Day4 (
    input: String,
) {
    var grid: MutableList<MutableList<Char>> = mutableListOf()

    init {
        grid = input
            .lines()
            .map { line ->
                line.map { char -> char }.toMutableList()
            }.toMutableList()
    }

    fun partOne(
        removeAccessible: Boolean = false,
    ): Int {
        val rows = grid.size
        val columns = grid[0].size
        val directions = listOf(
            0 to 1,
            1 to 0,
            0 to -1,
            -1 to 0,
            1 to 1,
            1 to -1,
            -1 to 1,
            -1 to -1,
        )

        var accessibleRolls = 0
        for (r in 0 until rows) {
            for (c in 0 until columns) {
                if (grid[r][c] != '@') {
                    continue
                }

                val adjacent = directions.count { (dr, dc) ->
                    val nr = r + dr
                    val nc = c + dc
                    nr in 0..<rows && nc in 0..<columns && grid[nr][nc] == '@'
                }

                if (adjacent < 4) {
                    if (removeAccessible) grid[r][c] = '.'
                    accessibleRolls++
                }

            }
        }

        return accessibleRolls
    }

    fun partTwo(): Any {

        var total = 0
        while(true) {
            val removed = partOne(
                removeAccessible = true,
            )
            if (removed == 0) {
                return total
            }
            total += removed
        }
    }
}

fun main() {
    val input = readInput("day04.txt")
    val sol = Day4(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}