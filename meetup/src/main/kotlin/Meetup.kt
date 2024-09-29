import java.time.DayOfWeek
import java.time.LocalDate

class Meetup(private val month: Int, private val year: Int) {
    fun day(dayOfWeek: DayOfWeek, schedule: MeetupSchedule): LocalDate {
        tailrec fun LocalDate.nextOfDayOfWeek(): LocalDate = if (this.dayOfWeek == dayOfWeek) {
            this
        } else {
            this.plusDays(1).nextOfDayOfWeek()
        }

        val earliestDatePossible = LocalDate.of(
            this.year, this.month, when (schedule) {
                MeetupSchedule.FIRST -> 1
                MeetupSchedule.SECOND -> 8
                MeetupSchedule.THIRD -> 15
                MeetupSchedule.FOURTH -> 22
                MeetupSchedule.LAST -> 1
                MeetupSchedule.TEENTH -> 13
            }
        )
        
        return if (schedule == MeetupSchedule.LAST) {
            earliestDatePossible.plusMonths(1).plusDays(-7)
        } else {
            earliestDatePossible
        }.nextOfDayOfWeek()
    }
}

