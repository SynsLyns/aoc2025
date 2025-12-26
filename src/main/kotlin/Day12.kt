import utils.readInput

class Day12(
    input: String,
) {
    var regions = listOf<Triple<Int, Int, List<Int>>>()
    var shapes = listOf<List<List<Char>>>()

    init {
        val data = input.split("\n\n")

        shapes = data.dropLast(1).map { shape ->
            shape.lines().drop(1).map {
                it.toList()
            }
        }

        regions = data.last().lines().map { line ->
            val (pre, post) = line.split(": ")
            val (width, length) = pre.split("x").map { it.toInt() }
            val presents = post.split(" ").map { it.toInt() }
            Triple(width, length, presents)
        }
    }

    fun partOne(): Any {
        val presentSizes = shapes.map { shape ->
            var size = 0
            for (row in shape) {
                for (c in row) {
                    if (c == '#') size++
                }
            }
            size
        }

        var count = 0
        for (region in regions) {
            val area = region.first * region.second
            var presentArea = 0
            for (i in region.third.indices) {
                presentArea += region.third[i] * presentSizes[i]
            }

            if (presentArea < area) {
                count++
            }
        }
        return count
    }
}

fun main() {
    val input = readInput("day12.txt")
    val sol = Day12(input)
    println("Part 1: ${sol.partOne()}")
}