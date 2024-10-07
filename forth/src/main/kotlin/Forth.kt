class Forth {
    fun evaluate(vararg line: String): List<Int> = line
        .fold(Parser()) { acc, it -> acc.parseLine(it) }
        .let { Evaluator(it.tokens).evaluate() }
}
