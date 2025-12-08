package utils

import java.io.File

fun readInput(name: String): String {
    return File("resources/$name").readText()
}