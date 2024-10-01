sealed class Token {
    class Number(val inner: Int) : Token()

    class Operation(val arity: Int, private val f: (List<Int>) -> List<Int>) : Token() {
        fun apply(arguments: List<Number>): List<Number> = arguments
            .map(Number::inner)
            .let(f)
            .map(::Number)
    }

    companion object {
        val OPERATIONS = mapOf<String, Operation>(
            "+" to Operation(2) { listOf(it[0] + it[1]) },
            "-" to Operation(2) { listOf(it[0] - it[1]) },
            "*" to Operation(2) { listOf(it[0] * it[1]) },
            "/" to Operation(2) {
                when (val b = it[1]) {
                    0 -> throw Exception("divide by zero")
                    else -> listOf(it[0] / b)
                }
            },
            "dup" to Operation(1) { listOf(it[0], it[0]) },
            "drop" to Operation(1) { listOf() },
            "swap" to Operation(2) { listOf(it[1], it[0]) },
            "over" to Operation(2) { listOf(it[0], it[1], it[0]) },
        )
    }
}
