sealed class LineItem {
    class Number(val inner: Int) : LineItem()

    class Command(val arity: Int, val f: (List<Int>) -> List<Int>) : LineItem()

    companion object {
        val COMMANDS = mapOf<String, Command>(
            "+" to Command(2) { listOf(it[0] + it[1]) },
            "-" to Command(2) { listOf(it[0] - it[1]) },
            "*" to Command(2) { listOf(it[0] * it[1]) },
            "/" to Command(2) {
                when (val b = it[1]) {
                    0 -> throw Exception("divide by zero")
                    else -> listOf(it[0] / b)
                }
            },
            "dup" to Command(1) { listOf(it[0], it[0]) },
            "drop" to Command(1) { listOf() },
            "swap" to Command(2) { listOf(it[1], it[0]) },
            "over" to Command(2) { listOf(it[0], it[1], it[0]) },
        )
    }
}
