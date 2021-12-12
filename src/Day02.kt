
class SubState3(private var x: Int = 0, private var y: Int = 0) {

    fun calculate(element: String): SubState3 {
        val (command, distance) = element.split(" ")

        when (command) {
            "forward" -> x += distance.toInt()
            "down" -> y += distance.toInt()
            "up" -> y -= distance.toInt()
            else -> println("Invalid command")
        }
        return this
    }

    fun xy() = x * y
}

class SubState4(private var x: Int = 0, private var y: Int = 0, private var aim: Int = 0) {

    fun calculate(element: String): SubState4 {
        val (command, distanceString) = element.split(" ")
        val distance = distanceString.toInt()

        when (command) {
            "forward" -> {
                x += distance
                y += aim * distance
            }
            "down" -> {
                aim += distance
            }
            "up" -> {
                aim -= distance
            }
            else -> println("Invalid command")
        }
        return this
    }

    fun xy() = x * y
}

fun main() {

    fun part1(input: List<String>): Int {
        return input
            .fold(SubState3()) { subState, element -> subState.calculate(element) }
            .xy()
    }

    fun part2(input: List<String>): Int {
        return input
            .fold(SubState4()) { subState, element -> subState.calculate(element) }
            .xy()
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
