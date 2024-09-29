enum class Classification {
    DEFICIENT, PERFECT, ABUNDANT
}

fun classify(naturalNumber: Int): Classification =
    when (naturalNumber) {
        in 1 until Int.MAX_VALUE -> when (aliquotSum(naturalNumber) - naturalNumber) {
            in Int.MIN_VALUE until 0 -> Classification.DEFICIENT
            0 -> Classification.PERFECT
            else -> Classification.ABUNDANT
        }

        else -> throw RuntimeException()
    }

fun aliquotSum(naturalNumber: Int): Int =
    (1 until naturalNumber)
        .filter { naturalNumber % it == 0 }
        .sum()