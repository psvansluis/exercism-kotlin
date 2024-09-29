import Orientation.NORTH

class Robot(
    var gridPosition: GridPosition = GridPosition(0, 0),
    var orientation: Orientation = NORTH,
) {

    fun simulate(instructions: String): Unit {
        instructions.fold(this, Robot::simulate).also {
            this.gridPosition = it.gridPosition
            this.orientation = it.orientation
        }
    }

    fun simulate(instruction: Char): Robot =
        when (instruction) {
            'A' -> Robot(advance(), orientation)
            'L' -> Robot(gridPosition, orientation.left)
            'R' -> Robot(gridPosition, orientation.right)
            else -> throw IllegalArgumentException("Invalid character '$instruction'")
        }

    private fun advance(): GridPosition = gridPosition.next(orientation)
}

//tailrec fun <T> T.repeat(
//    f: (T) -> T,
//    p: (T) -> Boolean,
//): T =
//    when {
//        p(this) -> f(this).repeat(f, p)
//        else -> this
//    }
//
//fun <T> T.repeat(f: (T) -> T, times: Int): T =
//    Pair(this, times)
//        .repeat(
//            { (first, second): Pair<T, Int> -> Pair(f(first), second - 1) },
//            { (_, second): Pair<T, Int> -> second > 0 })
//        .first
//
//infix fun <T> T.thrice(f: (T) -> T): T = this.repeat(f, 3)