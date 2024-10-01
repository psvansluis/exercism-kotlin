class Forth {
    private data class ParsedLines(
        val items: List<LineItem>,
        val commands: Map<String, List<LineItem>>,
    )

    fun evaluate(vararg line: String): List<Int> = line[0] // TODO: make work for multi-line
        .let { parseLine(ParsedLines(listOf(), mapOf()), it) }
        .items
        .let(::evaluate)

    private fun parseLine(acc: ParsedLines, line: String): ParsedLines =
        line
            .lowercase()
            .split("\\s+".toRegex())
            .map(::parseWord)
            .let { ParsedLines(acc.items + it, acc.commands) }

    private fun parseWord(word: String): LineItem = word
        .toIntOrNull()?.let(LineItem::Number)
        ?: LineItem.OPERATIONS[word]
        ?: throw Exception("undefined operation")

    private tailrec fun evaluate(lineItems: List<LineItem>): List<Int> =
        when (val firstOperationIndex = lineItems.indexOfFirst { it is LineItem.Operation }) {
            -1 -> lineItems.map { (it as LineItem.Number).inner }
            0 -> throw Exception("empty stack")
            else -> {
                val firstOperation = lineItems[firstOperationIndex] as LineItem.Operation
                val firstArgIndex = firstOperationIndex - firstOperation.arity
                if (firstArgIndex < 0) throw Exception("only one value on the stack")
                val pre = lineItems.take(firstArgIndex)
                val evaluated = lineItems
                    .slice(firstArgIndex until firstOperationIndex)
                    .map { it as LineItem.Number }
                    .let(firstOperation::apply)
                val post = lineItems.drop(firstOperationIndex + 1)
                evaluate(pre + evaluated + post)
            }
        }

}
