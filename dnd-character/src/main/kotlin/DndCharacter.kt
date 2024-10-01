import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random

class DndCharacter {

    val strength: Int = ability()
    val dexterity: Int = ability()
    val constitution: Int = ability()
    val intelligence: Int = ability()
    val wisdom: Int = ability()
    val charisma: Int = ability()
    val hitpoints: Int = 10 + modifier(constitution)

    companion object {

        fun ability(sides: Int = 6, times: Int = 4, drop: Int = 1): Int =
            generateSequence { 1 + Random.nextInt(sides) }
                .take(times)
                .sorted()
                .drop(drop)
                .sum()

        fun modifier(score: Int): Int = floor((score - 10).toFloat() / 2).roundToInt()
    }

}
