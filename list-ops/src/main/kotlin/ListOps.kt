fun <T> List<T>.customAppend(list: List<T>): List<T> = this + list

fun List<Any>.customConcat(): List<Any> = when (val head = firstOrNull()) {
    null -> this
    else -> when (head) {
        is List<*> -> (head as List<Any>).customConcat()
        else -> listOf(head)
    }
        .customAppend(drop(1).customConcat())
}

fun <T> List<T>.customFilter(predicate: (T) -> Boolean): List<T> = customFoldLeft(listOf()) { acc, el ->
    if (predicate(el)) acc + el else acc
}

val List<Any>.customSize: Int
    get() = when {
        isEmpty() -> 0
        else -> 1 + drop(1).customSize
    }

fun <T, U> List<T>.customMap(transform: (T) -> U): List<U> =
    customFoldLeft(listOf()) { acc, el -> acc + transform(el) }

fun <T, U> List<T>.customFoldLeft(initial: U, f: (U, T) -> U): U =
    firstOrNull()
        ?.let { drop(1).customFoldLeft(f(initial, it), f) }
        ?: initial


fun <T, U> List<T>.customFoldRight(initial: U, f: (T, U) -> U): U =
    lastOrNull()
        ?.let { dropLast(1).customFoldRight(f(it, initial), f) }
        ?: initial

fun <T> List<T>.customReverse(): List<T> = customFoldRight(listOf()) { el, acc -> acc + el }
