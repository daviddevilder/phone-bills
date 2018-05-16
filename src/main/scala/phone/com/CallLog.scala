package phone.com

final case class CallLog (customerId: String, phoneNumber: String, durationInSeconds: Int) {
  val callCostInPence: Double = durationInSeconds match {
    case x if x < 180 => x * 0.05
    case x => x * 0.03
  }
}

object CallLog {
  def createFromLineItem(line: String): Option[CallLog] = {
    line.split(" ") match {
      case Array(customerId, phoneNumber, duration) => Some(CallLog(customerId, phoneNumber, toSeconds(duration)))
      case _ => None
    }
  }

  def toSeconds(duration: String): Int = {
    duration.split(":") match {
      case Array(hours, minutes, seconds) => (hours.toInt * 3600) + (minutes.toInt * 60) + seconds.toInt
      case _ => 0
    }
  }
}
