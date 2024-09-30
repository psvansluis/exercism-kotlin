sealed class LineItem {
    class Number(val inner: Int) : LineItem() {

    }

    class Command(val argumentsNeeded: Int = 1, val f: (Number) -> LineItem) : LineItem()

    companion object {
        val COMMANDS = mapOf<String, Command>(
            "+" to Command(argumentsNeeded = 2, f = { a: Number -> Command { b: Number -> a + b } }),
            "-" to Command(argumentsNeeded = 2, f = { a: Number -> Command { b: Number -> b - a } }),
            "*" to Command(argumentsNeeded = 2, f = { a: Number -> Command { b: Number -> a * b } }),
            "*" to Command(argumentsNeeded = 2, f = { a: Number -> Command { b: Number -> a * b } }),
            "/" to Command(argumentsNeeded = 2, f = { a: Number ->
                when (a.inner) {
                    0 -> throw Exception("divide by zero")
                    else -> Command { b: Number -> b / a }
                }
            }),
        )
    }
}

operator fun LineItem.Number.plus(other: LineItem.Number): LineItem.Number = LineItem.Number(this.inner + other.inner)
operator fun LineItem.Number.minus(other: LineItem.Number): LineItem.Number = LineItem.Number(this.inner - other.inner)
operator fun LineItem.Number.times(other: LineItem.Number): LineItem.Number = LineItem.Number(this.inner * other.inner)
operator fun LineItem.Number.div(other: LineItem.Number): LineItem.Number = LineItem.Number(this.inner / other.inner)