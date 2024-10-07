class Parsed(
    val tokens: List<Token>,
    private val commands: Map<String, List<Token>>,
) {

    fun parseLine(line: String): Parsed = line
        .lowercase()
        .split("\\s+".toRegex())
        .let { parseWords(it) }

    private fun parseWords(words: List<String>): Parsed =
        when {
            words.firstOrNull() == ":" && words.lastOrNull() == ";" -> {
                val command = words.drop(1).dropLast(1)
                val key = command.firstOrNull()
                val value = command.drop(1).flatMap { parseWord(it, commands) }
                if (key == null || key.toIntOrNull() != null) throw Exception("illegal operation")
                Parsed(tokens, commands + (key to value))
            }

            else -> words
                .flatMap { parseWord(it, commands) }
                .let { Parsed(tokens + it, commands) }
        }

    private fun parseWord(word: String, commands: Map<String, List<Token>>): List<Token> = when (
        val int = word.toIntOrNull()
    ) {
        null -> commands[word]
            ?: Token.OPERATIONS[word]?.let(::listOf)
            ?: throw Exception("undefined operation")

        else -> listOf(Token.Number(int))
    }
}