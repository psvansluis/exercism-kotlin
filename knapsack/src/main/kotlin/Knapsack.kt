data class Item(val weight: Int?, val value: Int)

fun knapsack(maximumWeight: Int, items: List<Item>): Int {
    if (maximumWeight <= 0 || items.isEmpty()) return 0
    val (weight, value) = items[0]
    val tail = items.drop(1)
    val take = if (weight > maximumWeight) 0 else knapsack(maximumWeight - (weight ?: 0), tail) + value
    val leave = knapsack(maximumWeight, tail)
    return take.coerceAtLeast(leave)
}

