import java.util.LinkedList

class SubState1(private var count: Int = 0, private var previousDepth: Int = Int.MAX_VALUE) {

    fun calculate(element: Int) : SubState1 {
        if (element > this.previousDepth) {
            this.count++
        }
        this.previousDepth = element
        return this
    }

    fun count(): Int = count
}

class SubState2(
    private var numElementsToSum: Int = 3,
    private var count: Int = 0,
    private var previousDepthSum: Int = Int.MAX_VALUE,
    private val previousDepths: LinkedList<Int> = LinkedList<Int>()
    ) {

    fun calculate(element: Int) : SubState2 {
        this.previousDepths.addLast(element)

        if (this.previousDepths.size == numElementsToSum + 1) {
            this.previousDepths.removeFirst()
        }

        if (this.previousDepths.size == numElementsToSum) {
            val sum = this.previousDepths.sum()
            if (sum > previousDepthSum) {
                count++
            }
            previousDepthSum = sum
        }

        return this
    }

    fun count(): Int = count
}

fun main() {

    fun part1(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .fold(SubState1()) { subState, element -> subState.calculate(element) }
            .count()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toInt() }
            .fold(SubState2()) { subState, element -> subState.calculate(element) }
            .count()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
