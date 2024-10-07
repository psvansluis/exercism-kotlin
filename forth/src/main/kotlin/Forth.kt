class Forth {
    fun evaluate(vararg line: String): List<Int> = line
        .fold(Parser(), Parser::parseLine)
        .let { Evaluator(it.tokens).evaluate() }
}
