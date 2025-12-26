import utils.readInput
import kotlin.collections.map
import kotlin.text.trim

class Day1 (
    private val input: String,
    private val dialSize: Int
){
    var dial: Int = 50

    fun moveDial(amount: Int) {
        this.dial = (this.dial + amount).mod(dialSize)
    }

    fun partOne(): Any {
        this.dial = 50
        val lines = input.split("\n").map { it.trim() }

        var result = 0
        for (line in lines) {
            when (line[0]) {
                'R' -> moveDial(line.substring(1).toInt())
                'L' -> moveDial(-line.substring(1).toInt())
            }
            result += if (this.dial == 0) 1 else 0
        }
        return result
    }

    fun partTwo(): Any {
        this.dial = 50
        val lines = input.split("\n").map { it.trim() }

        var result = 0
        for (line in lines) {
            var moveAmount = line.substring(1).toInt()
            result += moveAmount / this.dialSize

            moveAmount %= this.dialSize

            if (moveAmount == 0) break

            moveAmount = when (line[0]) {
                'L' -> -moveAmount
                else -> moveAmount
            }

            result += if (this.dial + moveAmount < 0 || this.dial + moveAmount > this.dialSize - 1) 1 else 0
            moveDial(moveAmount)
        }
        if (this.dial == 0) result++
        return result
    }
}

fun main() {
    val input = readInput("day01.txt")
    val day1 = Day1(input, 100)
    println("Part 1: ${day1.partOne()}")
    println("Part 2: ${day1.partTwo()}")
}