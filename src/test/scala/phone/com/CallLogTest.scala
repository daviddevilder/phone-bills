package phone.com

import org.scalatest.{FlatSpec, Matchers}

class CallLogTest extends FlatSpec with Matchers {
  "callCostInPence" should "be charged at 0.05p per second when under 3 minutes duration" in {
    CallLog("A", "555-333-212", 179).callCostInPence should be(179 * 0.05)
  }

  it should "be charged at 0.03p per second when exactly 3 minutes duration" in {
    CallLog("A", "555-333-212", 180).callCostInPence should be(180 * 0.03)
  }

  it should "be charged at 0.03p per second when over 3 minutes duration" in {
    CallLog("A", "555-333-212", 181).callCostInPence should be(181 * 0.03)
  }

  "createFromLineItem" should "return an option of a CallLog object when passed a valid log file line entry" in {
    CallLog.createFromLineItem("A 555-333-212 00:02:03") should be(Some(CallLog("A", "555-333-212", 123)))
  }

  it should "return None when passed an empty log file line entry" in {
    CallLog.createFromLineItem("") should be(None)
  }

  it should "return None when passed a log file line entry with only one value" in {
    CallLog.createFromLineItem("INVALID") should be(None)
  }

  it should "return None when passed a log file line entry with only two values" in {
    CallLog.createFromLineItem("INVALID 123") should be(None)
  }

  it should "return None when passed a log file line entry with more than three values" in {
    CallLog.createFromLineItem("INVALID 123 1:1:1 INVALID") should be(None)
  }

  "toSeconds" should "return the number of seconds as an integer from a string representation (hh:mm:ss)" in {
    CallLog.toSeconds("01:23:45") should be(5025)
  }

  it should "return 0 when the string is empty" in {
    CallLog.toSeconds("") should be(0)
  }

  it should "return 0 when the string represents 0 seconds" in {
    CallLog.toSeconds("00:00:00") should be(0)
  }

  it should "return 0 when the string is not in the correct format (hh:mm:ss)" in {
    CallLog.toSeconds("012345") should be(0)
  }
}
