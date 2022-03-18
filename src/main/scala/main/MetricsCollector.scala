package main

import scala.util.matching.Regex

class MetricsCollector(private val pattern: Regex, private val metricName: String) {

  private var sum, max = 0.0D
  private var count = 0

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

  def multiplyBy(value:Int): Unit = {
    sum*=value
    count*=value
  }

  def printRawResult(): Unit = {
    println(s"Average $metricName: [${sum / count}], Max $metricName: [${max}] with sum: [$sum] and count: [$count]")
  }

  def printByteFormattedResult(): Unit = {
    val formattedAvg = formatByteValue(sum / count)
    val formattedMax = formatByteValue(max)
    val formattedSum = formatByteValue(sum)
    println(s"Average $metricName: [$formattedAvg], Max $metricName: [$formattedMax] with sum: [$formattedSum] and count: [$count]")
  }

  def formatByteValue(sizeInBytes: Double): String = {
    val sizeInText =
      if (sizeInBytes >= Constants.Kilobytes && sizeInBytes < Constants.Megabytes) {
        sizeInBytes.toFloat / Constants.Kilobytes + " KB"
      }
      else if (sizeInBytes >= Constants.Megabytes && sizeInBytes < Constants.Gigabytes) {
        sizeInBytes.toFloat / Constants.Megabytes + " MB"
      }
      else if (sizeInBytes >= Constants.Gigabytes) {
        sizeInBytes.toFloat / Constants.Gigabytes + " GB"
      }
      else {
        sizeInBytes + " B"
      }
    sizeInText
  }

}
