package phone.com

import java.text.NumberFormat
import java.util.Locale

trait ConsolePrinter {
  val british = new Locale("en", "GB")

  def printReportLine(customerName: String, totalCostInPence: Double): Unit = {
    val formattedTotalCost = NumberFormat.getCurrencyInstance(british).format(totalCostInPence/100)
    print(s"""Customer Name: ${customerName} | Cost: ${formattedTotalCost}""")
  }

  def print(s: String): Unit = {
    println(s)
  }
}
