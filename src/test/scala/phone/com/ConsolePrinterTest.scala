package phone.com

import java.util.Locale

import org.scalatest.{FlatSpec, Matchers}

class ConsolePrinterTest extends FlatSpec with Matchers {
  "british" should "be setup as a British Locale" in {
    val testObject = new Object() with ConsolePrinter
    testObject.british should be(new Locale("en", "GB"))
  }

  "printReportLine" should "print to the console in the correct format" in {
    val testObject = new Object() with MockConsolePrinter
    testObject.printReportLine("customer name 1", 12.34)
    testObject.consoleOutput should be(Seq("Customer Name: customer name 1 | Cost: £0.12"))
  }

  trait MockConsolePrinter extends ConsolePrinter {
    var consoleOutput: Seq[String] = Seq()
    override def print(s: String): Unit = {
      consoleOutput = consoleOutput :+ s
    }
  }
}
