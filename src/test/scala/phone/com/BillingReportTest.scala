package phone.com

import org.scalatest.{FlatSpec, Matchers}

class BillingReportTest extends FlatSpec with Matchers {
  "run" should "group call logs by each customer, remove the highest cost call per customer and print the report" in {
    val callLogs: List[CallLog] = List(
      CallLog("A", "555-333-212", 123),
      CallLog("A", "555-333-213", 250),
      CallLog("B", "555-333-214", 360),
      CallLog("B", "555-333-215", 460),
      CallLog("A", "555-333-216", 840)
    )
    val billingReport = new BillingReport(callLogs) with MockConsolePrinter
    billingReport.run()
    billingReport.consoleOutput should be(Seq("Customer Name: A | Cost: £0.14", "Customer Name: B | Cost: £0.11"))
  }

  it should "not print anything to the console if there are no call log entries" in {
    val callLogs: List[CallLog] = List()
    val billingReport = new BillingReport(callLogs) with MockConsolePrinter
    billingReport.run()
    billingReport.consoleOutput should be(Seq())
  }

  it should "show customer bills as £0.00 when they have only made one call" in {
    val callLogs: List[CallLog] = List(
      CallLog("A", "555-333-212", 123),
      CallLog("B", "555-333-214", 360)
    )
    val billingReport = new BillingReport(callLogs) with MockConsolePrinter
    billingReport.run()
    billingReport.consoleOutput should be(Seq("Customer Name: A | Cost: £0.00", "Customer Name: B | Cost: £0.00"))
  }

  "removeHighestCostCall" should "remove the most expensive call log from a list of call logs" in {
    val callLogs: List[CallLog] = List(
      CallLog("A", "555-333-211", 1000),
      CallLog("A", "555-333-212", 2000),
      CallLog("A", "555-333-213", 3000)
    )
    BillingReport(callLogs).removeHighestCostCall(callLogs) should be(
      List(CallLog("A", "555-333-211", 1000), CallLog("A", "555-333-212", 2000))
    )
  }

  it should "remove the most expensive call log from a list of call logs of varying tariffs" in {
    val callLogs: List[CallLog] = List(
      CallLog("A", "555-333-211", 179),
      CallLog("A", "555-333-212", 180),
      CallLog("A", "555-333-213", 181)
    )
    BillingReport(List()).removeHighestCostCall(callLogs) should be(
      List(CallLog("A", "555-333-212", 180), CallLog("A", "555-333-213", 181))
    )
  }

  "printTotalCost" should "print a line to the console for that customer and total cost" in {
    val customerCalls = ("customer name", List(
      CallLog("customer name", "555-333-211", 20),
      CallLog("customer name", "555-333-212", 20)
    ))
    val billingReport = new BillingReport(List()) with MockConsolePrinter
    billingReport.printTotalCost(customerCalls)
    billingReport.consoleOutput should be(Seq("Customer Name: customer name | Cost: £0.02"))
  }

  sealed trait MockConsolePrinter extends ConsolePrinter {
    var consoleOutput: Seq[String] = Seq()

    override def print(s: String): Unit = {
      consoleOutput = consoleOutput :+ s
    }
  }

}
