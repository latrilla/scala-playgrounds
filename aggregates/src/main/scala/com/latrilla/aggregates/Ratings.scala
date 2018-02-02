package com.latrilla.aggregates

import com.latrilla.Utils._

/**
  * Useless for now, will contain the aggregates with computations
  */
class Ratings extends Dumpable {
  var storage: List[(Any, Any, Any, Any)] = List()
  var maxDate: Long = 0

  def store(userId: Int, itemId: Int, rating: Int, timestamp: Long): Unit = {
    if (timestamp > maxDate) maxDate = timestamp
    storage = (userId, itemId, rating, timestamp) :: storage
  }

  def computePenalty(rating: Int, ts: Long): Float = (rating * Math.pow(0.95, daysBetweenTimestamps(ts, maxDate))).toFloat

  def aggregate(): Map[(Int, Int), Float] = storage.foldLeft(Map[(Int, Int), Float]())
    {
      (m: Map[(Int, Int), Float], seq) => {
        val Tuple4(uId: Int, pId: Int, rating: Int, ts: Long) = seq
        val adjustedRating: Float = computePenalty(rating, ts) + m.getOrElse((uId, pId), 0.toFloat)
        m.updated((uId, pId), adjustedRating)
    }
  }

  override def toDumpSeq: Seq[Seq[String]] = {
    this.aggregate().view.filter({ case (keys, ratingSum) => ratingSum > 0.01 })
      .map({ case (k, v) => Seq(k._1.toString, k._2.toString, v.toString) }).toSeq
  }
}
