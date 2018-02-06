package com.latrilla.aggregates

import java.io._

import com.latrilla.Utils._

object Main extends App {


  override def main(args: Array[String]): Unit = {
    val userLookup: IntLookup[String] = new IntLookup[String]()
    val productLookup: IntLookup[String] = new IntLookup[String]()
    val ratings: Ratings = new Ratings()


    try {
      val bufferedReader = new BufferedReader(new FileReader(args(0)))
      memInfo()
      time(readAndStore(bufferedReader, userLookup, productLookup, ratings), "readAndStore")
      memInfo()
      time(writeCsv(userLookup, productLookup, ratings), "dump all")
      memInfo()
    }
    catch {
      case e: FileNotFoundException => println("Invalid file path")
      case e: IndexOutOfBoundsException => println("Must provide a file name\nUsage: sbt \"run [file path]\"")
      case e: MatchError => println("Invalid line format")
    }
  }

  def writeCsv(userLookup: IntLookup[String], productLookup: IntLookup[String], ratings: Ratings): Unit = {
    time(userLookup.write("userlookup.csv"), "dump users")
    time(productLookup.write("productlookup.csv"), "dump product")
    time(ratings.write("aggratings.csv"), "dump aggregates")
  }

  def readAndStore(reader: BufferedReader, usersLookup: IntLookup[String], productLookup: IntLookup[String], ratings: Ratings): Unit = {
    var line: Option[String] = None
    while ({
      line = Option(reader.readLine)
      line.isDefined
    }) {
      val Array(userId, productId, rating, timestamp) = line.get.split(",").map(_.trim)
      val userIdAsInteger = usersLookup.store(userId)
      val productIdAsInteger = productLookup.store(productId)
      ratings.store(userIdAsInteger, productIdAsInteger, rating.toInt, timestamp.toLong)
    }
  }


}
