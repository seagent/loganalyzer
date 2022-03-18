package main

import scala.io.Source

object LogAnalyzer {
  private val QUERY_PROCESSING_PATTERN = """Federated query has been performed in: \[(.*)\] milliseconds""".r
  private val RESULT_CHANGE_PATTERN = """, and notified in \[(.*)\] milliseconds""".r
  private val MEMORY_USAGE_PATTERN = """Used heap: (.*) MB""".r
  private val CPU_USAGE_PATTERN = """Load: (.*) \(""".r
  private val MESSAGE_SIZE_PATTERN = """ is: \[(.*)\] Bytes, and is """.r
  private val MESSAGE_SENT_THROUGH_NETWORK_PATTERN = """Size of the """

  def analyzeLogFiles(folderPath: String): Unit = {
    println(s"\n\nCalculation for [$folderPath] has started.")
    println("--------------------------------------------------------------------------------------------------------\n")
    println("Results for Node-1")
    analyzeServerResources(folderPath + "MonARCh-Node-1.log")
    println("\n--------------------------------------------------------------------------------------------------------")
    println("Results for Node-2")
    analyzeServerResources(folderPath + "MonARCh-Node-2.log")
    println("\n--------------------------------------------------------------------------------------------------------")
    println("Evaluation Information")
    printEvaluationInfo(folderPath)
    println("\n--------------------------------------------------------------------------------------------------------")
    println(s"Calculation for [$folderPath] has ended.")
  }

  def analyzeNetworkCost(folderPath: String): Unit = {
    val firstSource = Source.fromResource(folderPath + "MonARCh-Node-1.log")
    val secondSource = Source.fromResource(folderPath + "MonARCh-Node-2.log")
    val messageLogs = firstSource.getLines().toList.filter(_.contains(MESSAGE_SENT_THROUGH_NETWORK_PATTERN)) ::: secondSource.getLines().toList.filter(_.contains(MESSAGE_SENT_THROUGH_NETWORK_PATTERN))
    val messageSizeCollector = new MetricsCollector(MESSAGE_SIZE_PATTERN, "Overall Message Size")
    messageLogs.foreach(messageSizeCollector.calculate)
    messageSizeCollector.multiplyBy(100)
    messageSizeCollector.printByteFormattedResult()
  }

  private def analyzeServerResources(filePath: String) = {
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
    qpCollector.printRawResult()
    rcCollector.printRawResult()
    memoryCollector.printRawResult()
    cpuCollector.printRawResult()
  }

  private def printEvaluationInfo(folderPath: String): Unit = {
    val source = Source.fromFile(folderPath + "EvaluationInfo.txt")
    for (line <- source.getLines()) {
      println(line)
    }
    source.close()
  }
}
