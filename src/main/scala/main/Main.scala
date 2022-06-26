package main

import scala.io.Source
import scala.util.matching.Regex

object Main {

  def main(args: Array[String]): Unit = {
    LogAnalyzer.analyzeLogFiles(getFilePathFor("Most-Selective-Query","MIN","5000-Query"))
    LogAnalyzer.analyzeNetworkCost("network-logs/MOST/MIN/",1000,5000)
    //LogAnalyzer.analyzeLogFiles(getFilePathFor("Least-Selective-Query","ALL","200-Query"))
    //LogAnalyzer.analyzeLogFiles(getFilePathFor("Least-Selective-Query","ALL","300-Query"))
    /*LogAnalyzer.analyzeNetworkCost("analyzing-network-logs/LEAST/ALL/",10,100)
    println("-------------------------------------------------------------")
    LogAnalyzer.analyzeNetworkCost("analyzing-network-logs/MOST/300/",700, 7000)*/
  }

  private def getFilePathFor(dataSetId: Int, nodeId: Int): String = {
    s"Monarch-Evaluation-Results/$dataSetId/MonARCh-$dataSetId-Node-$nodeId.log"
  }

  private def getFilePathFor(querySelectivity: String, companySize: String, queryCount: String): String = {
    s"/Users/burakyonyul/Documents/Monarch/Evaluation-Results-Monarch/$querySelectivity/$companySize/$queryCount/"
  }

}
