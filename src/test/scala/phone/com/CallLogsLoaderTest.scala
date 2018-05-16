package phone.com

import org.scalatest.{FlatSpec, Matchers}

class CallLogsLoaderTest extends FlatSpec with Matchers {
  "filePath" should "be set to the correct file location" in {
    val testObject = new Object() with CallLogsLoader
    testObject.filePath should be("src/main/resources/calls.log")
  }

  "callLogs" should "produce a list of CallLog objects from a log file" in {
    sealed trait TestCallLogsLoader extends CallLogsLoader {
      override val filePath: String = "src/test/resources/calls-test.log"
    }

    val testObject = new Object() with TestCallLogsLoader
    testObject.callLogs should be(
      List(CallLog("A", "555-333-212", 123), CallLog("B", "555-433-242", 401), CallLog("C", "555-433-242", 63))
    )
  }

  it should "produce an empty list from an empty log file" in {
    sealed trait EmptyCallLogsLoader extends CallLogsLoader {
      override val filePath: String = "src/test/resources/calls-empty.log"
    }

    val testObject = new Object() with EmptyCallLogsLoader
    testObject.callLogs should be(List())
  }
}
