package com.latrilla

import java.io._

import com.latrilla.Utils._
import com.latrilla.aggregates.{IntLookup, Ratings}

object Main extends App {


  override def main(args: Array[String]): Unit = {
    val userLookup: IntLookup[String] = new IntLookup()
    val productLookup: IntLookup[String] = new IntLookup()
    val ratings: Ratings = new Ratings()


    try {
      val bufferedReader = new BufferedReader(new FileReader(args(0)))
      memInfo()
      time(readAndStore(bufferedReader, userLookup, productLookup, ratings), "readAndStore")
      memInfo()
      time(dump(userLookup, productLookup, ratings), "dump all")
      memInfo()
    }
    catch {
      case e: FileNotFoundException => println("Invalid file path")
      case e: IndexOutOfBoundsException => println("Must provide a file name\nUsage: sbt \"run [file path]\"")
      case e: MatchError => println("Invalid line format")
    }
  }

  def dump(userLookup: IntLookup[String], productLookup: IntLookup[String], ratings: Ratings): Unit = {
    time(userLookup.dump("userlookup.csv"), "dump users")
    time(productLookup.dump("productlookup.csv"), "dump product")
    time(ratings.dump("aggratings.csv"), "dump aggregates")
  }

  def readAndStore(reader: BufferedReader, usersLookup: IntLookup[String], productLookup: IntLookup[String], ratings: Ratings): Unit = {
    var line: String = null
    while ( {
      line = reader.readLine
      line != null
    }) {
      val Array(userId, itemId, rating, timestamp) = line.split(",").map(_.trim)
      ratings.store(usersLookup.store(userId), productLookup.store(itemId), rating.toInt, timestamp.toLong)
    }
  }


}
