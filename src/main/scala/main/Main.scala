package main

import scala.io.Source
import scala.util.matching.Regex

object Main {

  def main(args: Array[String]): Unit = {
    LogAnalyzer.analyze(getFilePathFor(500,1))
    LogAnalyzer.analyze(getFilePathFor(500,2))
    LogAnalyzer.analyze(getFilePathFor(1000,1))
    LogAnalyzer.analyze(getFilePathFor(1000,2))
    LogAnalyzer.analyze(getFilePathFor(1500,1))
    LogAnalyzer.analyze(getFilePathFor(1500,2))
    LogAnalyzer.analyze(getFilePathFor(2000,1))
    LogAnalyzer.analyze(getFilePathFor(2000,2))
    LogAnalyzer.analyze(getFilePathFor(2500,1))
    LogAnalyzer.analyze(getFilePathFor(2500,2))
    LogAnalyzer.analyze(getFilePathFor(3000,1))
    LogAnalyzer.analyze(getFilePathFor(3000,2))
    LogAnalyzer.analyze(getFilePathFor(3500,1))
    LogAnalyzer.analyze(getFilePathFor(3500,2))
  }

  private def getFilePathFor(dataSetId: Int, nodeId: Int): String = {
    s"/home/burak/Development/Monarch-Results/$dataSetId/MonARCh-$dataSetId-Node-$nodeId.log"
  }

}
