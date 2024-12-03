/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import java.io.File
import java.nio.CharBuffer
import kotlin.collections.first

fun main() {
    val file = File("src/main/resources/listExo.txt")
    val list = file.readLines().toString().withoutDisabled().toUncorrupted()
    
    val result = list.sum()
    
    println(result)
}

fun String.toUncorrupted(): List<Int> {
    val regex = Regex("""mul\([0-9]{1,3},[0-9]{1,3}\)""")
    return regex.findAll(this).map {
        it.groupValues.map {
            it.substringAfter("mul(").substringBefore(",").toInt() * it.substringAfter(",").substringBefore(")").toInt()
        }.sum()
    }.toList()
}

fun String.withoutDisabled(): String {
    val regex = Regex("""don\'t\(\).*?do\(\)""")
    return regex.replace(this) { "" }
}
