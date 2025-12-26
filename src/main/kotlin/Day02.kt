import utils.readInput
import kotlin.collections.map

class Day2 (
    input: String,
) {
    var ranges: List<Pair<Long, Long>> = mutableListOf()

    init {
        ranges = input.split(',')
            .map { range ->
                val (a, b) = range.split("-")
                a.toLong() to b.toLong()
            }
    }

    fun partOne(): Any {
        var result = 0L
        for (pair in ranges) {
            for (i in pair.first..pair.second) {
                val s = i.toString()
                if (s.length % 2 != 0) continue
                if (s.take(s.length / 2) == s.substring(s.length / 2)) {
                    result += i
                }
            }
        }
        return result
    }

    fun partTwo(): Any {
        var result = 0L
        for (pair in ranges) {
            for (i in pair.first..pair.second) {
                val s = i.toString()
                val len = s.length
                for (j in 1 .. len / 2) {
                    val substring = s.take(j)
                    var start = j
                    var end = j + j
                    while (end < len) {
                        if (substring != s.substring(start, end)) break
                        start = end
                        end = start + j
                    }
                    if (end == s.length && substring == s.substring(start, end)) {
                        result += i
                        break
                    }
                }
            }
        }
        return result
    }
}

fun main() {
    val input = readInput("day02.txt")
    val sol = Day2(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}