class RailFenceCipher(private val rails: Int) {
    init {
        require(rails > 1) { "Rails must be greater than 1" }
    }

    private fun railIndex(charIndex: Int): Int {
        val patternSize: Int = 2 + ((rails - 2) * 2)
        val i = charIndex % patternSize
        return if (i < rails) i else patternSize - i
    }

    private fun makeEncryptor(
        firstSorter: (Coordinate) -> Int,
        secondSorter: (Coordinate) -> Int,
    ): (String) -> String = { input: String ->
        makeCoordinates(input.length)
            .sortedBy(firstSorter)
            .zip(input.toList())
            .sortedBy { secondSorter(it.first) }
            .joinToString(separator = "") { it.second.toString() }
    }

    private fun makeCoordinates(length: Int): List<Coordinate> =
        (0 until length).map { Coordinate(it, railIndex(it)) }

    private data class Coordinate(val x: Int, val y: Int)

    fun getEncryptedData(input: String): String = makeEncryptor(Coordinate::x, Coordinate::y)(input)

    fun getDecryptedData(input: String): String = makeEncryptor(Coordinate::y, Coordinate::x)(input)
}