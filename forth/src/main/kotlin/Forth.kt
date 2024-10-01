class Forth {
    private data class ParsedLines(
        val items: List<LineItem>,
        val commands: Map<String, List<LineItem>>,
    )

    fun evaluate(vararg line: String): List<Int> = line
        .fold(ParsedLines(listOf(), mapOf()), ::parseLine)
        .items
        .let(::evaluate)

    private fun parseLine(acc: ParsedLines, line: String): ParsedLines =
        line
            .lowercase()
            .split("\\s+".toRegex())
            .let { words: List<String> ->
                if (words.firstOrNull() == ":" && words.lastOrNull() == ";") {
                    val command = words.drop(1).dropLast(1)
                    val name = command.firstOrNull()
                    val value = command.drop(1).flatMap { parseWord(it, acc.commands) }
                    if (name == null || name.toIntOrNull() != null) throw Exception("illegal operation")
                    ParsedLines(acc.items, acc.commands + (name to value))
                } else {
                    words
                        .flatMap { parseWord(it, acc.commands) }
                        .let { ParsedLines(acc.items + it, acc.commands) }
                }
            }


    private fun parseWord(word: String, commands: Map<String, List<LineItem>>): List<LineItem> = when (
        val int = word.toIntOrNull()
    ) {
        null -> commands[word]
            ?: LineItem.OPERATIONS[word]?.let(::listOf)
            ?: throw Exception("undefined operation")

        else -> listOf(LineItem.Number(int))
    }


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
