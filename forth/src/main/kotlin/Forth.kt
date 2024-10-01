class Forth {
    private data class ParsedLines(
        val tokens: List<Token>,
        val commands: Map<String, List<Token>>,
    )

    fun evaluate(vararg line: String): List<Int> = line
        .fold(ParsedLines(listOf(), mapOf()), ::parseLine)
        .tokens
        .let(::evaluate)

    private fun parseLine(acc: ParsedLines, line: String): ParsedLines = line
        .lowercase()
        .split("\\s+".toRegex())
        .let { parseWords(acc, it) }

    private fun parseWords(acc: ParsedLines, words: List<String>): ParsedLines =
        when {
            words.firstOrNull() == ":" && words.lastOrNull() == ";" -> {
                val command = words.drop(1).dropLast(1)
                val key = command.firstOrNull()
                val value = command.drop(1).flatMap { parseWord(it, acc.commands) }
                if (key == null || key.toIntOrNull() != null) throw Exception("illegal operation")
                ParsedLines(acc.tokens, acc.commands + (key to value))
            }

            else -> words
                .flatMap { parseWord(it, acc.commands) }
                .let { ParsedLines(acc.tokens + it, acc.commands) }
        }

    private fun parseWord(word: String, commands: Map<String, List<Token>>): List<Token> = when (
        val int = word.toIntOrNull()
    ) {
        null -> commands[word]
            ?: Token.OPERATIONS[word]?.let(::listOf)
            ?: throw Exception("undefined operation")

        else -> listOf(Token.Number(int))
    }


    private tailrec fun evaluate(tokens: List<Token>): List<Int> =
        when (val firstOperationIndex = tokens.indexOfFirst { it is Token.Operation }) {
            -1 -> tokens.map { (it as Token.Number).inner }
            0 -> throw Exception("empty stack")
            else -> {
                val firstOperation = tokens[firstOperationIndex] as Token.Operation
                val firstArgIndex = firstOperationIndex - firstOperation.arity
                if (firstArgIndex < 0) throw Exception("only one value on the stack")
                val pre = tokens.take(firstArgIndex)
                val evaluated = tokens
                    .slice(firstArgIndex until firstOperationIndex)
                    .map { it as Token.Number }
                    .let(firstOperation::apply)
                val post = tokens.drop(firstOperationIndex + 1)
                evaluate(pre + evaluated + post)
            }
        }

}
