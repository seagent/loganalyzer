package main

import scala.util.matching.Regex

class MetricsCollector(private val pattern: Regex, private val metricName: String) {

  var sum, max = 0.0D
  var count = 0

  def calculate(text: String): Unit = {
    pattern.findAllIn(text).matchData foreach {
      m =>
        val value = m.group(1).toDouble
        sum += value
        count += 1
        if (value > max)
          max = value
    }
  }

  def printRawResult(): Unit = {
    println(s"Average $metricName: [${sum / count}], Max $metricName: [${max}] with sum: [$sum] and count: [$count]")
  }

}
