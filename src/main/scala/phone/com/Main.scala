package phone.com

object Main extends App with CallLogsLoader {
  BillingReport(callLogs).run()
}