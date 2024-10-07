class Evaluator(tokens: List<Token>) {
    private val pre: List<Token.Number>
    private val args: List<Token.Number>
    private val operation: Token.Operation? =
        tokens.firstOrNull { it is Token.Operation }?.let { it as Token.Operation }
    private val post: List<Token> = tokens.dropWhile { it is Token.Number }.drop(1)

    init {
        val preAndArgs = tokens.takeWhile { it is Token.Number }.map { it as Token.Number }
        val arity = operation?.arity ?: 0
        val f = { g: (List<Token.Number>, Int) -> List<Token.Number> -> g(preAndArgs, arity) }
        pre = f(List<Token.Number>::dropLast)
        args = f(List<Token.Number>::takeLast)
    }

    fun evaluate(): List<Int> = when {
        operation == null -> pre.map(Token.Number::inner)
        args.isEmpty() -> throw Exception("empty stack")
        args.size < operation.arity -> throw Exception("only one value on the stack")
        else -> Evaluator(pre + operation.apply(args) + post).evaluate()
    }
}