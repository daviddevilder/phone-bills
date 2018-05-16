package phone.com

import scala.io.Source

trait CallLogsLoader {
  val filePath: String = "src/main/resources/calls.log"

  final lazy val callLogs: List[CallLog] = {
    Source.fromFile(filePath).getLines.toList
      .flatMap(line => CallLog.createFromLineItem(line))
  }
}