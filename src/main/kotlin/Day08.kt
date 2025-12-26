import utils.readInput
import kotlin.collections.map
import kotlin.math.pow
import kotlin.math.sqrt

data class Point3D(val x: Double, val y: Double, val z: Double) {
    fun straightLineDistance(other: Point3D): Double {
        return sqrt(
            (this.x - other.x).pow(2) +
            (this.y - other.y).pow(2) +
            (this.z - other.z).pow(2)
        )
    }
}

data class Edge(val p1: Int, val p2: Int, val dist: Double)

class UnionFind(
    n: Int
) {
    private val parent = IntArray(n) { it }
    private val size = IntArray(n) { 1 }

    fun getParent(a: Int): Int {
        if (parent[a] != a) parent[a] = getParent(parent[a])
        return parent[a]
    }

    fun union(a: Int, b: Int): Boolean {
        val pa = getParent(a)
        val pb = getParent(b)
        if (pa == pb) return false
        if (size[pa] < size[pb]) {
            parent[pa] = pb
            size[pb] += size[pa]
            return size[pb] == size.size
        } else {
            parent[pb] = pa
            size[pa] += size[pb]
            return size[pa] == size.size
        }
    }

    fun circuitSizes(): List<Int> = size.filterIndexed { index, _ -> parent[index] == index }
}

class Day8 (
    input: String,
) {
    val points: List<Point3D> = input
        .lines()
        .map { line ->
            val (x, y, z) = line.trim().split(",")
            Point3D(x.toDouble(), y.toDouble(), z.toDouble())
        }

    val edges = mutableListOf<Edge>()

    init {
        for (i in points.indices) {
            for (j in i + 1 until points.size) {
                edges.add(Edge(i, j, points[i].straightLineDistance(points[j])))
            }
        }
        edges.sortBy { it.dist }
    }

    fun partOne(): Any {
        val uf = UnionFind(points.size)
        for (i in 0 until 1000) {
            val (p1, p2, _) = edges[i]
            uf.union(p1, p2)
        }
        return uf.circuitSizes().sortedDescending().take(3).reduce { acc, i -> acc * i }
    }

    fun partTwo(): Any {
        val uf = UnionFind(points.size)
        for (edge in edges) {
            val (p1, p2, _) = edge
            if (uf.union(p1, p2)) {
                return points[p1].x.toLong() * points[p2].x.toLong()
            }
        }
        return -1
    }
}

fun main() {
    val input = readInput("day08.txt")
    val sol = Day8(input)
    println("Part 1: ${sol.partOne()}")
    println("Part 2: ${sol.partTwo()}")
}