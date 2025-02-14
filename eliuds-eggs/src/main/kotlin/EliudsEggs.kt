object EliudsEggs {

    tailrec fun eggCount(number: Int, acc: Int = 0): Int = when (number) {
        0 -> acc
        else -> eggCount(
            number / 2,
            acc + number % 2
        )
    }
}
