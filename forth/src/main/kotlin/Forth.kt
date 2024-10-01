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
        if (lineItems.all { it is LineItem.Number }) {
            return lineItems.map { (it as LineItem.Number).inner }
        } else {
            val firstCommandIndex = lineItems.indexOfFirst { it is LineItem.Command }
            if (firstCommandIndex == 0) {
                throw Exception("empty stack")
            }
            val firstCommand = lineItems[firstCommandIndex] as LineItem.Command
            if (firstCommand.arity > firstCommandIndex) {
                throw Exception("only one value on the stack")
            }
            val numbersForCalculation = lineItems
                .slice((firstCommandIndex - firstCommand.arity) until firstCommandIndex)
            val evaluated = firstCommand
                .f(numbersForCalculation.map { (it as LineItem.Number).inner })
                .map(LineItem::Number)
            val pre = lineItems.takeWhile { it != firstCommand }.dropLast(firstCommand.arity)
            val post = lineItems.dropWhile { it != firstCommand }.drop(1)
            return evaluate(pre + evaluated + post)
        }
    }
}
