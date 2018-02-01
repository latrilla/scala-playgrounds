package com.latrilla

import java.io._

import com.latrilla.Utils._
import com.latrilla.aggregates.UUIDToIntegerLookup

object Main extends App {


  override def main(args: Array[String]): Unit = {
    val usersLookup: UUIDToIntegerLookup = new UUIDToIntegerLookup()
    val productLookup: UUIDToIntegerLookup = new UUIDToIntegerLookup()

    try {
      val bufferedReader = new BufferedReader(new FileReader(args(0)))
      time(readAndStore(bufferedReader, usersLookup, productLookup), "readAndStore")
      memInfo()
      time(usersLookup.dump("userlookup.csv", "userId"), "dump users")
      memInfo()
      time(productLookup.dump("productlookup.csv", "itemId"), "dump product")
      memInfo()
    }
    catch {
      case e: FileNotFoundException => println("Invalid file path")
      case e: IndexOutOfBoundsException => println("Must provide a file name\nUsage: sbt \"run [file path]\"")
      case e: MatchError => println("Invalid line format")
    }
  }

  def readAndStore(reader: BufferedReader, usersLookup: UUIDToIntegerLookup, productLookup: UUIDToIntegerLookup): Unit = {
    var line: String = null
    while ( {
      line = reader.readLine; line != null
    }) {
      val Array(userId, itemId, rating, timestamp) = line.split(",").map(_.trim)
      usersLookup.store(userId)
      productLookup.store(itemId)
    }
  }


}
