class Forth {
    fun evaluate(vararg line: String): List<Int> = line
        .fold(ParsedLines(listOf(), mapOf())) { acc, it -> acc.parseLine(it) }
        .let { Chunked(it.tokens).evaluate() }
}
