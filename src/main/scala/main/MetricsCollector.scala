package main

import scala.util.matching.Regex

class MetricsCollector(private val pattern: Regex, private val metricName: String) {

  private var sum = 0.0D
  private var count = 0

  def calculate(text: String): Unit = {
    pattern.findAllIn(text).matchData foreach {
      m =>
        sum += m.group(1).toDouble
        count += 1
    }
  }

  def printResult(): Unit = {
    println(s"Average $metricName: [${sum / count}] with sum: [$sum] and count: [$count]")
  }

}
