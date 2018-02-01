package com.latrilla.aggregates

import java.io.{BufferedWriter, FileWriter}

class UUIDToIntegerLookup {
  var storage: Map[String, Int] = Map()

  def store(userId: String): Unit = {
      if (!(storage contains userId)) {
        storage += (userId -> storage.size)
      }
  }

  def get(userId: String): Option[Int] ={
    storage.get(userId)
  }

  def dump(filename: String, columnname: String): Unit = {
    val outputFile = new BufferedWriter(new FileWriter(filename))
    storage.toSeq
      .map(tu => Seq(tu._1.toString, tu._2.toString))
      .foreach(record => outputFile.write(record.mkString("", ",", "\n")))
  }


}
