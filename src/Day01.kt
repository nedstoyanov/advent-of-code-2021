import java.util.LinkedList

data class SubState1(var count: Int, var previousDepth: Int) {

    fun calculate(element: Int) : SubState1 {
        if (element > this.previousDepth) {
            this.count++
        }
        this.previousDepth = element
        return this
    }
}

data class SubState2(
    var count: Int,
    var previousDepthSum: Int,
    val previousDepths: LinkedList<Int>
    ) {

    fun calculate(element: Int) : SubState2 {
        this.previousDepths.addLast(element)

        if (this.previousDepths.size == 4) {
            this.previousDepths.removeFirst()
        }

        if (this.previousDepths.size == 3) {
            val sum = this.previousDepths.sum()
            if (sum > previousDepthSum) {
                count++
            }
            previousDepthSum = sum
        }

        return this
    }
}

fun main() {

    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .fold(SubState1(0, Int.MAX_VALUE)) { subState: SubState1, element: Int ->
                subState.calculate(element)
            }
            .count
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .fold(SubState2(0, Int.MAX_VALUE, LinkedList<Int>())) { subState: SubState2, element: Int ->
                subState.calculate(element)
            }
            .count
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
