/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import java.io.File

fun main() {
    val file = File("src/main/resources/listExo.txt")
    val list = file.readLines().map { 
        val split = it.split("   ")
        split[0].toInt() to split[1].toInt()
    }
    val left = list.map{ it.first }.sorted()
    val right = list.map{ it.second }.sorted()
    
    val result = left.mapIndexed { index, value -> Math.abs(value - right[index]) }.sum()
    
    println(result)
    
    val similarity = left.map {
        right.filter { r -> r == it }.sum()
    }.sum()
    
    println(similarity)
}