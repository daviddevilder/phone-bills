package phone.com

case class BillingReport(callLogs: List[CallLog]) extends ConsolePrinter {
  def run(): Unit = {
    val chargeableCallLogsByCustomer: Map[String, List[CallLog]] =
      callLogs
        .groupBy(_.customerId)
        .mapValues(removeHighestCostCall)

    chargeableCallLogsByCustomer foreach printTotalCost
  }

  def removeHighestCostCall(callLogs: List[CallLog]): List[CallLog] = {
    callLogs.sortBy(_.callCostInPence).dropRight(1)
  }

  def printTotalCost(customerCalls: (String, List[CallLog])): Unit = {
    printReportLine(customerCalls._1, customerCalls._2.map(_.callCostInPence).sum)
  }
}
