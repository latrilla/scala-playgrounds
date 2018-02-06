package com.latrilla

import java.sql.Timestamp
;

/*
Imported from https://github.com/akhld/scala-csv-lookup/blob/master/src/main/scala-2.12/Utils.scala
 */
object Utils {

  val MILLIS_TO_DAYS = 86400000 // 1000*60*60*24, number of milliseconds in a day

  def memInfo(): Unit = {
    System.gc()
    val mem = Runtime.getRuntime.totalMemory() - Runtime.getRuntime.freeMemory()
    println(new Timestamp(System.currentTimeMillis()).toString + " Used memory : " + mem / 1024.0 / 1024.0 + " MB")
  }

  def time[R](block: => R, step: String): R = {
    val t0 = System.nanoTime()
    val result = block // call-by-name
    val t1 = System.nanoTime()
    println(step + " | Elapsed time: " + (t1 - t0) + "ns")
    result
  }

  def daysBetweenTimestamps(t1: Long, t2: Long): Int = (Math.abs(t2 - t1) / MILLIS_TO_DAYS).intValue()
}
