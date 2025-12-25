import utils.readInput
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Point2D(var x: Long, var y: Long) {
    fun squareArea(other: Point2D): Long {
        return (abs(this.x - other.x) + 1) * (abs(this.y - other.y) + 1)
    }
}

class Day9 (
    input: String,
) {
    val points: List<Point2D> = input
        .lines()
        .map { line ->
            val (x, y) = line.trim().split(",")
            Point2D(x.toLong(), y.toLong())
        }

    fun partOne(): Any {
        var maxArea = 0L
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                maxArea = max(maxArea, points[i].squareArea(points[j]))
            }
        }
        return maxArea
    }

    fun intersects(p1: Point2D, p2: Point2D): Boolean {
        val maxY = max(p1.y, p2.y)
        val maxX = max(p1.x, p2.x)
        val minY = min(p1.y, p2.y)
        val minX = min(p1.x, p2.x)

        for (i in 0 until points.size) {
            val e1 = points[i]
            val e2 = points[(i + 1) % points.size]

            if (e1.x in (minX + 1)..<maxX && e1.y in (minY + 1)..<maxY) {
                return true
            }

            if (e1.x == e2.x) {
                if (
                    e1.x in (minX + 1)..<maxX &&
                    max(e1.y, e2.y) > minY &&
                    min(e1.y, e2.y) < maxY
                ) {
                    return true
                }
            } else if (e1.y == e2.y) {
                if (
                    e1.y in (minY + 1)..<maxY &&
                    max(e1.x, e2.x) > minX &&
                    min(e1.x, e2.x) < maxX
                ) {
                    return true
                }
            }
        }

        return false
    }

    fun partTwo(): Any {
        var maxArea = 0L
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                val area = points[i].squareArea(points[j])
                if (area <= maxArea) {
                    continue
                }
                if (!intersects(points[i], points[j])) {
                    maxArea = area
                }
            }
        }
        return maxArea
    }
}

fun main() {
    val input = readInput("day09.txt")
    val sol = Day9(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}