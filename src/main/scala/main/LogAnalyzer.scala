package main

import scala.io.Source

object LogAnalyzer {
  private val QUERY_PROCESSING_PATTERN = """Federated query has been performed in: \[(.*)\] milliseconds""".r
  private val RESULT_CHANGE_PATTERN = """, and notified in \[(.*)\] milliseconds""".r
  private val MEMORY_USAGE_PATTERN = """Used heap: (.*) MB""".r
  private val CPU_USAGE_PATTERN = """Load: (.*) \(""".r

  def analyze(filePath: String): Unit = {
    println("\n\n---------------------------------------------------------------\n")
    println(s"Calculation for [$filePath] has started.")
    val qpCollector = new MetricsCollector(QUERY_PROCESSING_PATTERN, "query processing time")
    val rcCollector = new MetricsCollector(RESULT_CHANGE_PATTERN, "change notification time")
    val memoryCollector = new MetricsCollector(MEMORY_USAGE_PATTERN, "memory usage")
    val cpuCollector = new MetricsCollector(CPU_USAGE_PATTERN, "cpu usage")
    val source = Source.fromFile(filePath)
    for (line <- source.getLines()) {
      qpCollector.calculate(line)
      rcCollector.calculate(line)
      memoryCollector.calculate(line)
      cpuCollector.calculate(line)
    }
    source.close()
    qpCollector.printResult()
    rcCollector.printResult()
    memoryCollector.printResult()
    cpuCollector.printResult()
    println(s"Calculation for [$filePath] has ended.")
    println("---------------------------------------------------------------")
  }

}
