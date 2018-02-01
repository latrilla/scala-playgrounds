package com.latrilla

/*
Imported from https://github.com/akhld/scala-csv-lookup/blob/master/src/main/scala-2.12/Utils.scala
 */
object Utils {
  def memInfo(): Unit = {
    System.gc()
    val mem = Runtime.getRuntime.totalMemory() - Runtime.getRuntime.freeMemory()
    println("Used memory : " + mem / 1024.0 / 1024.0 + " MB")
  }

  def time[R](block: => R, step: String): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    println(step + " | Elapsed time: " + (t1 - t0) + "ns")
    result
  }
}
