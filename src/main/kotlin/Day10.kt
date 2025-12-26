import com.google.ortools.Loader
import com.google.ortools.linearsolver.MPSolver
import utils.readInput

class Day10(
    private val input: String,
) {
    fun partOne(): Any {
        var res = 0
        for (line in input.lines()) {
            val data = line.split(" ")
            val diagram = data[0].trim('[', ']').toCharArray().map {
                it == '#'
            }
            val target = diagram.foldIndexed(0) { idx, acc, b ->
                if (b) acc or (1 shl idx) else acc
            }

            val buttons = data.drop(1).dropLast(1).map { button ->
                button.trim('(', ')').split(",").map {
                    it.toInt()
                }.fold(0) { acc, i -> acc or (1 shl i) }
            }


            val q = ArrayDeque<Pair<Int, Int>>()
            val seen = mutableSetOf<Int>()
            q.addLast(0 to 0)
            seen.add(0)

            while (q.isNotEmpty()) {
                val (lights, step) = q.removeFirst()
                if (lights == target) {
                    res += step
                    break
                }

                for (button in buttons) {
                    val newLights = lights xor button
                    if (newLights !in seen) {
                        seen.add(newLights)
                        q.addLast(newLights to step + 1)
                    }
                }
            }

        }
        return res
    }

    fun partTwo(): Any {
        Loader.loadNativeLibraries()
        var res = 0
        for (line in input.lines()) {
            val data = line.split(" ")

            val desired = data.last().trim('{', '}').split(',').map {
                it.toInt()
            }
            val buttons = data.drop(1).dropLast(1).map { button ->
                button.trim('(', ')').split(",").map { it.toInt() }
            }

            val vars = buttons.size

            val solver = MPSolver.createSolver("SCIP")
            val x = Array(vars) { j -> solver.makeIntVar(0.0, Double.POSITIVE_INFINITY, "x$j") }

            desired.forEachIndexed { i, target ->
                val constraint = solver.makeConstraint(target.toDouble(), target.toDouble())
                buttons.forEachIndexed { j, b ->
                    if (i in b) constraint.setCoefficient(x[j], 1.0)
                }
            }

            val objective = solver.objective()
            x.forEach { objective.setCoefficient(it, 1.0) }
            objective.minimization()

            solver.solve()

            res += x.sumOf { it.solutionValue().toInt() }
        }

        return res
    }
}

fun main() {
    val input = readInput("day10.txt")
    val sol = Day10(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}