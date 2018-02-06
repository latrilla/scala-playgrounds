package com.latrilla.aggregates

import java.io.{BufferedWriter, FileWriter}

/**
  * Classes implementing this trait can be outputed as a file.
  * To do this, one must override the toDumpSeq which will be the list of what will be written
  */
trait Writable {

  /**
    * Writes the result of #toDumpSeq as a csv file at the given file path
    * @param filename output file name
    */
  final def write(filename: String): Unit = {
    val outputFile = new BufferedWriter(new FileWriter(filename))
    this.toWriteSeq.foreach(record => outputFile.write(record.mkString("", ",", "\n")))
  }

  /**
    *
    * @return
    */
  def toWriteSeq: Seq[Seq[String]]
}
