package org.example

import org.example.GuardDirection.*
import java.io.File

fun main() {
    val file = File("app/src/main/resources/listExo.txt")
    val map = file.readLines().map { it.toMutableList() }.toMutableList()
    val previousStates = guardPatrol(map)?.distinctBy { it.first }?.toHashSet()
    if (previousStates != null) {
        println("Number of positions ${previousStates.size}")
    }
    previousStates?.also {
        searchForGuardLoops(it, map)
    }
}

fun searchForGuardLoops(
    previousStates: HashSet<Pair<Pair<Int, Int>, GuardDirection>>,
    map: MutableList<MutableList<Char>>
) {
    val loopNumber = previousStates.count { (pos, _) ->
        val (y, x) = pos
        if (map[y][x] != '.') return@count false
        map[y][x] = '#'
        return@count guardPatrol(map).also { map[y][x] = '.' } == null
    }
    println("Number of Loops $loopNumber")
}

private fun guardPatrol(map: MutableList<MutableList<Char>>): HashSet<Pair<Pair<Int, Int>, GuardDirection>>? {
    var guardPosition = 0 to 0
    map.forEachIndexed { index, line ->
        if (line.contains(UP.position)) {
            guardPosition = index to line.indexOf(UP.position)
        }
    }
    var guardDirection = UP
    val maxY = map.size
    val maxX = map.first().size
    val previousStates = hashSetOf<Pair<Pair<Int, Int>, GuardDirection>>()
    do {
        val currentState = guardPosition to guardDirection
        if (currentState in previousStates) return null //LOOP identified
        previousStates.add(currentState)
        val (y, x) = guardPosition
        val (nextY, nextX) = when (guardDirection) {
            UP -> y - 1 to x
            RIGHT -> y to x + 1
            DOWN -> y + 1 to x
            LEFT -> y to x - 1
        }
        if (isOut(nextY, nextX, maxY, maxX)) break
        if (map[nextY][nextX] == '#') guardDirection = guardDirection.rotateRight()
        else guardPosition = nextY to nextX
    } while (true)
    return previousStates // NO LOOP
}

fun isOut(nextY: Int, nextX: Int, maxY: Int, maxX: Int): Boolean =
    nextY < 0 || nextY >= maxY || nextX < 0 || nextX >= maxX

enum class GuardDirection(var position: Char) {
    UP('^'),
    DOWN('v'),
    LEFT('<'),
    RIGHT('>');

    fun rotateRight(): GuardDirection =
        when (this) {
            UP -> RIGHT
            RIGHT -> DOWN
            DOWN -> LEFT
            LEFT -> UP
        }
}