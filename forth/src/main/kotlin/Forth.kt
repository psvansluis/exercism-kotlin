class Forth {
    data class Parsed(val integers: List<Int>, val commands: List<LineItem.Command>)

    fun evaluate(
        vararg line: String,
        acc: Pair<List<Int>, List<LineItem.Command>>,
    ): List<Int> {
        if (line.isEmpty()) {
            return emptyList()
        } else {
            val first = line.first()
            val rest = line.drop(1)
            val (firstInts, firstCommands) = evaluate(first, customCommands)

        }
    }

    private fun evaluate(
        line: String,
        customCommands: List<LineItem.Command>,
    ): Pair<List<Int>, List<LineItem.Command>> {
        TODO()
    }
}
