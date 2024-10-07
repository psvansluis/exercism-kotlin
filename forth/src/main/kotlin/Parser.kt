class Parser(
    val tokens: List<Token> = listOf(),
    private val commands: Map<String, List<Token>> = mapOf(),
) {

    fun parseLine(line: String): Parser = line
        .lowercase()
        .split("\\s+".toRegex())
        .let(::parseWords)

    private fun parseWords(words: List<String>): Parser =
        when {
            words.firstOrNull() == ":" && words.lastOrNull() == ";" -> with(words.drop(1).dropLast(1)) {
                val key = firstOrNull()?.takeIf { it.toIntOrNull() == null }
                    ?: throw Exception("illegal operation")
                val value = drop(1).flatMap(::parseWord)
                this@Parser + (key to value)
            }

            else -> this + words.flatMap(::parseWord)
        }

    private fun parseWord(word: String): List<Token> = when (
        val int = word.toIntOrNull()
    ) {
        null -> commands[word]
            ?: Token.OPERATIONS[word]?.let(::listOf)
            ?: throw Exception("undefined operation")

        else -> listOf(Token.Number(int))
    }

    operator fun plus(other: List<Token>): Parser = Parser(
        tokens = tokens + other,
        commands = commands
    )

    operator fun plus(other: Pair<String, List<Token>>): Parser = Parser(
        tokens = tokens,
        commands = commands + other
    )
}