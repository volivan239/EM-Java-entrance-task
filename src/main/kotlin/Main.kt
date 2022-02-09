fun main(args: Array<String>) {
    val nTimes = args[0].toInt()
    val results = IOSpeedTester().doTests(nTimes)
    results.forEachIndexed { ind, value ->
        println("Test #${ind + 1}: Writing speed = ${value.first} MB/s, Reading speed = ${value.second} MB/s")
    }
    println("\nAvg Writing speed = ${results.sumOf { it.first } / nTimes} MB/s," +
               " Reading speed = ${results.sumOf { it.second } / nTimes} MB/s")
}
