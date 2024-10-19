package tt.game.m2m.util

import scalafx.util.Duration

object CommonUtil {

  def durationToTimerString(duration: Duration): String = {
    val minute: Int = duration.toMinutes.toInt
    val second: Int = (duration.toSeconds % 60).toInt
    val formattedSecond: String = "%02d".format(second)
    s"$minute:$formattedSecond"
  }

}
