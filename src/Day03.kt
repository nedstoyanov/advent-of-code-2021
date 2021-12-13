class SubState5(
    private var oneCounts: Array<Int> = Array(12) { 0 },
    private var zeroCounts: Array<Int> = Array(12) { 0 }
) {

    fun calculate(element: String): SubState5 {
        for (ch in element.withIndex()) {
            when (ch.value) {
                '1' -> oneCounts[ch.index] += 1
                '0' -> zeroCounts[ch.index] += 1
                else -> println("Invalid character: ${ch.value} in string $element")
            }
        }
        return this
    }

    fun powerConsumption(): Int = gamma() * epsilon()

    private fun flipBits(bitString: String): String = bitString.map {
        when (it) {
            '0' -> '1'
            '1' -> '0'
            else -> println("Invalid character: $it in string $bitString")
        }
    }.joinToString("")

    private fun gamma(): Int {
        val mostCommonBits = mostCommonBits()
        return Integer.parseInt(mostCommonBits, 2)
    }

    private fun epsilon(): Int {
        val mostCommonBits = mostCommonBits()
        val flippedBits = flipBits(mostCommonBits)
        return Integer.parseInt(flippedBits, 2)
    }

    private fun mostCommonBits(): String = zeroCounts.zip(oneCounts) { zeroCount, oneCount ->
        if (zeroCount >= oneCount) '0'
        else '1'
    }.joinToString("")
}

class SubState6(
    private val digitCount: Int = 12,
    private var oneCounts: Array<Int> = Array(digitCount) { 0 },
    private var zeroCounts: Array<Int> = Array(digitCount) { 0 }
) {

    fun calculate(element: String): SubState6 {
        for (ch in element.withIndex()) {
            when (ch.value) {
                '1' -> oneCounts[ch.index] += 1
                '0' -> zeroCounts[ch.index] += 1
                else -> println("Invalid character: ${ch.value} in string $element")
            }
        }

        return this
    }

    fun zeroCounts() = zeroCounts

    fun oneCounts() = oneCounts
}

fun main() {

    fun part1(input: List<String>): Int {
        return input
            .fold(SubState5()) { subState, element -> subState.calculate(element) }
            .powerConsumption()
    }

    fun getOxygenRatingFilter(i: Int, zeroCounts: Array<Int>, oneCounts: Array<Int>) = if (oneCounts[i] >= zeroCounts[i]) {
        '1'
    } else {
        '0'
    }

    fun getCo2ScrubberFilter(i: Int, zeroCounts: Array<Int>, oneCounts: Array<Int>) = if (oneCounts[i] < zeroCounts[i]) {
        '1'
    } else {
        '0'
    }

    fun filterNums(index: Int, numbers: List<String>, filterFn: (Int, Array<Int>, Array<Int>) -> Char): Int {
        val counts = numbers.fold(SubState6()) { subState, element -> subState.calculate(element) }
        val filteredNums = numbers.filter { it[index] == filterFn(index, counts.zeroCounts(), counts.oneCounts()) }

        if (filteredNums.size == 1) {
            return Integer.parseInt(filteredNums.first(), 2)
        }

        return filterNums(index + 1, filteredNums, filterFn)
    }

    fun co2ScrubberRating(input: List<String>): Int = filterNums(0, input, ::getCo2ScrubberFilter)

    fun oxygenGeneratorRating(input: List<String>): Int = filterNums(0, input, ::getOxygenRatingFilter)

    fun lifeSupportRating(input: List<String>): Int = oxygenGeneratorRating(input) * co2ScrubberRating(input)

    fun part2(input: List<String>): Int {
        return lifeSupportRating(input)
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
