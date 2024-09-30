class Forth {

    fun evaluate(
        vararg line: String,
    ): List<Int> {
        return line[0] // TODO: make work for multi-line
            .let(::parse)
            .let(::evaluate)
    }

    fun parse(line: String): List<LineItem> = line
        .split("\\s+".toRegex())
        .map(::parseWord)

    private fun parseWord(word: String): LineItem = word
        .toIntOrNull()?.let(LineItem::Number)
        ?: LineItem.COMMANDS[word]
        ?: throw Exception("undefined operation")

    private tailrec fun evaluate(
        lineItems: List<LineItem>,
    ): List<Int> {
        if (lineItems.all { it is LineItem.Number }) {
            return lineItems.map { (it as LineItem.Number).inner }
        } else {
            val firstCommandIndex = lineItems.indexOfFirst { it is LineItem.Command }
            val firstCommand = lineItems[firstCommandIndex] as LineItem.Command
            val lastNumber = lineItems.getOrNull(firstCommandIndex - 1) as LineItem.Number?
                ?: throw Exception("empty stack")
            if (firstCommand.argumentsNeeded > firstCommandIndex) {
                throw Exception("only one value on the stack")
            }
            val evaluated = firstCommand.f(lastNumber)
            val pre = lineItems.takeWhile { it != firstCommand }.dropLast(1)
            val post = lineItems.dropWhile { it != firstCommand }.drop(1)
            return evaluate(pre + evaluated + post)
        }
    }
}
