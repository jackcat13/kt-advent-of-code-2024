/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import java.io.File
import java.util.LinkedList
import kotlin.jvm.internal.iterator

fun main() {
    val rules = mutableListOf<Pair<Int, Int>>()
    var areRulesRed = false
    val toPrint = mutableListOf<List<Int>>()

    val file = File("app/src/main/resources/listExo.txt")
    file.readLines().forEach {
        if (it.trim().isBlank()) {
            areRulesRed = true
            return@forEach
        }
        if (areRulesRed.not()) {
            val rule = it.split("|").let { it[0].toInt() to it[1].toInt() }
            rules.add(rule)
        } else {
            toPrint.add(it.trim().split(",").toList().map { it.toInt() })
        }
    }

    println("Rules : $rules")
    println("toPrint : $toPrint")

    val toPrintSorted = toPrint.filter { it.isValid(rules).not() }.map { it.sort(rules) }.toMutableList()

    println("toPrintSorted : $toPrintSorted")

    val result1 = asValid(toPrint, rules)
    val result2 = asValid(toPrintSorted, rules)

    println("Result $result1")
    println("Result $result2")
}

private fun asValid(
    toPrint: MutableList<List<Int>>,
    rules: MutableList<Pair<Int, Int>>
): Int {
    return toPrint.map {
        var r = 0;
        if (it.isValid(rules)) {
            println("Valid! $it")
            r += it[it.size.floorDiv(2)]
        }
        r
    }.sum()
}

// Bubble sort in inefficient but it was to complete the exercise in a shorter time
fun List<Int>.sort(rules: List<Pair<Int, Int>>): List<Int> {
    val sorted = this.toMutableList()
    var notOrdered = false
    while (notOrdered.not()) {
        notOrdered = true
        for (i in indices) {
            for (j in i + 1..<this.size) {
                if (i == j) continue
                if (sorted[j] to sorted[i] in rules) {
                    val swap = sorted[i]
                    sorted[i] = sorted[j]
                    sorted[j] = swap
                    notOrdered = false
                }
            }
        }
    }
    return sorted
}

fun List<Int>.isValid(rules: List<Pair<Int, Int>>): Boolean {
    var result = true
    val iterator = this.iterator()
    var previous = iterator.next()
    for (next in iterator) {
        if (previous to next !in rules) {
            result = false
            break
        }
        previous = next
    }
    return result
}
