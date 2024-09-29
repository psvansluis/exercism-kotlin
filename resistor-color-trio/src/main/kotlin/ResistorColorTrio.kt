import kotlin.math.pow

object ResistorColorTrio {

    fun text(vararg input: Color): String = format(value(*input))

    private fun format(value: Int): String = when (value) {
        in 1_000_000..Int.MAX_VALUE -> (value / 1_000_000).toString() + " mega"
        in 1_000..1_000_000 -> (value / 1_000).toString() + " kilo"
        else -> "$value "
    } + "ohms"

    private fun value(vararg colors: Color): Int {
        val prefix = colors[0].ordinal * 10 + colors[1].ordinal
        val exponent = 10.0.pow(colors[2].ordinal.toDouble()).toInt()
        return prefix * exponent
    }
}
