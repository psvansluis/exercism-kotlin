class Forth {

    fun evaluate(
        vararg line: String,
    ): List<Int> {
        return line[0] // TODO: make work for multi-line
            .let(::parse)
            .let(::evaluate)
    }

    fun parse(line: String): List<LineItem> = line
        .lowercase()
        .split("\\s+".toRegex())
        .map(::parseWord)

    private fun parseWord(word: String): LineItem = word
        .toIntOrNull()?.let(LineItem::Number)
        ?: LineItem.COMMANDS[word]
        ?: throw Exception("undefined operation")

    private tailrec fun evaluate(
        lineItems: List<LineItem>,
    ): List<Int> {
        when (val firstCommandIndex = lineItems.indexOfFirst { it is LineItem.Command }) {
            -1 -> return lineItems.map { (it as LineItem.Number).inner }
            0 -> throw Exception("empty stack")
            else -> {
                val firstCommand = lineItems[firstCommandIndex] as LineItem.Command
                val firstArgIndex = firstCommandIndex - firstCommand.arity
                if (firstArgIndex < 0) {
                    throw Exception("only one value on the stack")
                }
                val pre = lineItems.take(firstArgIndex)
                val evaluated =
                    lineItems
                        .slice(firstArgIndex until firstCommandIndex)
                        .map { (it as LineItem.Number).inner }
                        .let(firstCommand.f)
                        .map(LineItem::Number)
                val post = lineItems.drop(firstCommandIndex + 1)
                return evaluate(pre + evaluated + post)
            }
        }
    }
}
