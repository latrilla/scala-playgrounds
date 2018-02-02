package com.latrilla.aggregates

import java.io.{BufferedWriter, FileWriter}

trait Dumpable {
  def dump(filename: String): Unit = {
    val outputFile = new BufferedWriter(new FileWriter(filename))
    this.toDumpSeq.foreach(record => outputFile.write(record.mkString("", ",", "\n")))
  }

  def toDumpSeq: Seq[Seq[String]]
}
