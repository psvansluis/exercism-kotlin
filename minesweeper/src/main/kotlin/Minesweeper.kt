class MinesweeperBoard(board: List<String>) {
    private val board: List<List<Field>> = board.map(Field.Companion::parseRow)

    private fun annotate(): List<List<Field>> =
        board
            .map(::annotateHorizontal)
            .let(::annotateVertical)
            .annotateLeftDiagonals()
            .let(::annotateRightDiagonals)


    private fun transpose(board: List<List<Field>>): List<List<Field>> {
        if (board.flatten().isEmpty()) return board
        val heads = board.map(List<Field>::first)
        val tails = board.map { it.drop(1) }
        if (tails.flatten().isEmpty()) return listOf(heads)
        return listOf(heads) + transpose(tails)
    }

    private fun annotateVertical(board: List<List<Field>>): List<List<Field>> =
        board
            .let(::transpose)
            .map(::annotateHorizontal)
            .let(::transpose)

    private fun annotateLeft(row: List<Field>): List<Field> =
        row.mapIndexed(makeIncrementor(row))

    private fun annotateBottomLeft(previousRow: List<Field>, thisRow: List<Field>): List<Field> =
        thisRow.mapIndexed(makeIncrementor(previousRow))

    private fun makeIncrementor(row: List<Field>) = { index: Int, field: Field ->
        when (row.getOrNull(index + 1)) {
            is Field.Mine -> field.increment()
            else -> field
        }
    }

    private fun annotateBottomLeft(board: List<List<Field>>): List<List<Field>> =
        board.mapIndexed { index, row ->
            board
                .getOrNull(index - 1)
                ?.let { annotateBottomLeft(it, row) }
                ?: row
        }

    private fun List<List<Field>>.annotateLeftDiagonals(): List<List<Field>> =
        this
            .let(::annotateBottomLeft)
            .reversed()
            .let(::annotateBottomLeft)
            .reversed()


    private fun annotateRightDiagonals(board: List<List<Field>>): List<List<Field>> =
        board
            .map(List<Field>::reversed)
            .annotateLeftDiagonals()
            .map(List<Field>::reversed)

    private fun annotateHorizontal(row: List<Field>): List<Field> =
        row
            .let(::annotateLeft)
            .reversed()
            .let(::annotateLeft)
            .reversed()

    fun withNumbers(): List<String> =
        annotate()
            .map { row: List<Field> -> row.joinToString("") }

}