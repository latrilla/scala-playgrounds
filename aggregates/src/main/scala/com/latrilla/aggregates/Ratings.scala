package com.latrilla.aggregates

import com.latrilla.Utils._

import scala.collection.mutable.ListBuffer

/**
  * Useless for now, will contain the aggregates with computations
  */
class Ratings extends Writable {
  val storage: ListBuffer[RatingsEntry] = ListBuffer.empty
  private var maxDate: Long = 0

  def store(userId: Int, itemId: Int, rating: Int, timestamp: Long): Unit = {
    this.maxDate = Math.max(this.maxDate, timestamp)
    storage += RatingsEntry(userId, itemId, rating, timestamp)
  }

  def computePenalty(rating: Int, ts: Long): Float = (rating * Math.pow(0.95, daysBetweenTimestamps(ts, maxDate))).toFloat

  def aggregate: Map[(Int, Int), Float] = storage.foldLeft(Map[(Int, Int), Float]()) {
    (m: Map[(Int, Int), Float], entry: RatingsEntry) => {
      val adjustedRating: Float = computePenalty(entry.rating, entry.timestamp) + m.getOrElse((entry.userIdAsInteger, entry.productIdAsInteger), 0.toFloat)
      m.updated((entry.userIdAsInteger, entry.productIdAsInteger), adjustedRating)
    }
  }

  override def toWriteSeq: Seq[Seq[String]] = this.aggregate.view
      .filter({ case (keys, ratingSum) => ratingSum > 0.01 })
      .map({ case (k, v) => Seq(k._1.toString, k._2.toString, v.toString) })
      .toSeq

}

case class RatingsEntry(userIdAsInteger: Int, productIdAsInteger: Int, rating: Int, timestamp: Long)
