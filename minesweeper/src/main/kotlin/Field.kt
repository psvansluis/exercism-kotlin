sealed class Field {

    abstract override fun toString(): String
    abstract fun increment(): Field
    class Open(private val mineNeighbours: Int) : Field() {
        override fun toString(): String = when (mineNeighbours) {
            0 -> " "
            else -> "$mineNeighbours"
        }

        override fun increment(): Open = Open(mineNeighbours + 1)
    }

    object Mine : Field() {
        override fun toString(): String = "*"
        override fun increment(): Field = this
    }

    companion object {
        fun fromChar(char: Char): Field =
            when (char) {
                ' ' -> Open(0)
                '*' -> Mine
                else -> throw IllegalArgumentException("$char is not a valid Field")
            }

        fun parseRow(row: String): List<Field> =
            row.map(Companion::fromChar)
    }
}