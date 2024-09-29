enum class Orientation {

    NORTH, EAST, SOUTH, WEST;


}

inline fun <reified T : Enum<T>> right(): T = T::values()[(this.ordinal + 1) % values().size]

inline val left: Orientation
    get() = values()[(this.ordinal + values().size - 1) % values().size]
