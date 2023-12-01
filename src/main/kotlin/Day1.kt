import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.concurrent.atomic.AtomicInteger

fun day1Part1() {
    val file = File("src/main/resources/aoc2023-1.1.txt")
    val sum = AtomicInteger(0)
    runBlocking {
        file.readLines().forEach {
            launch(Dispatchers.Default) {
                val digits = it.filter { it.isDigit() }
                val code = "${digits.first()}${digits.last()}".toInt()
                sum.addAndGet(code)
            }
        }
    }
    println("Day 1 Part 1 answer: $sum")
}

fun day1Part2() {
    val file = File("src/main/resources/aoc2023-1.1.txt")
    val sum = AtomicInteger(0)
    runBlocking {
        file.readLines().forEach {
            launch(Dispatchers.Default) {
                var first: Int? = null
                var last: Int? = null
                it.toMutableList().forEachIndexed { index, c ->
                    it.checkForNumberAsText(index)?.let {
                        if (first == null) {
                            first = it
                        }
                        last = it
                    } ?: run {
                        if (c.isDigit()) {
                            if (first == null) {
                                first = c.digitToInt()
                            }
                            last = c.digitToInt()
                        }
                    }
                }
                val code = "$first$last".toInt()
                sum.addAndGet(code)
            }
        }
    }
    println("Day 1 Part 2 answer: $sum")
}

private fun String.checkForNumberAsText(start: Int): Int? {
    numbersAsText.forEachIndexed { index, s ->
        if (s.length + start <= length && substring(start until start + s.length) == s) {
            return index
        }
    }
    return null
}

private val numbersAsText = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")