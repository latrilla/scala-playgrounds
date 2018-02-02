package com.latrilla.aggregates

import com.latrilla.Utils
import org.scalatest._

class RatingsTest extends FlatSpec with Matchers {

  "A ratings" should "aggregate correctly results" in {
    val ratings: Ratings = new Ratings()
    val (uId1: Int, uId2: Int, pId1: Int, pId2: Int) = (1, 2, 11, 22)
    ratings.store(uId1, pId1, 2, System.currentTimeMillis())
    ratings.store(uId1, pId1, 4, System.currentTimeMillis())
    ratings.store(uId1, pId2, 1, System.currentTimeMillis())
    ratings.store(uId1, pId2, 1, System.currentTimeMillis())
    ratings.store(uId2, pId1, 20, System.currentTimeMillis())
    ratings.store(uId2, pId1, 40, System.currentTimeMillis())

    ratings.aggregate() should be(Map((uId1, pId1) -> 6, (uId1, pId2) -> 2, (uId2, pId1) -> 60))
  }

  "A ratings" should "correctly compute a rating penalty" in {
    val ratings: Ratings = new Ratings()
    val (uId1: Int, pId1: Int) = (1, 11)
    val ts_now = System.currentTimeMillis()
    val ts_2_days_before = ts_now - 2l * Utils.MILLIS_TO_DAYS
    ratings.store(uId1, pId1, 2, ts_now)
    ratings.store(uId1, pId1, 4, ts_2_days_before)

    ratings.computePenalty(4, ts_2_days_before) should be((4 * Math.pow(0.95, 2)).toFloat)
  }

  "A ratings" should "correctly output his dump sequence" in {
    val ratings: Ratings = new Ratings()
    val (uId1: Int, uId2: Int, pId1: Int, pId2: Int) = (1, 2, 11, 22)
    ratings.store(uId1, pId1, 2, System.currentTimeMillis())
    ratings.store(uId1, pId1, 4, System.currentTimeMillis())
    ratings.store(uId1, pId2, 1, System.currentTimeMillis())
    ratings.store(uId1, pId2, 1, System.currentTimeMillis())
    ratings.store(uId2, pId1, 20, System.currentTimeMillis())
    ratings.store(uId2, pId1, 40, System.currentTimeMillis())

    ratings.toDumpSeq should be(Stream(Seq("2", "11", "60.0"), Seq("1", "22", "2.0"), Seq("1", "11", "6.0")))
  }


  "A ratings" should "not dump low ratings (<0.01)" in {
    val ratings: Ratings = new Ratings()
    val (uId1: Int, uId2: Int, pId1: Int, pId2: Int) = (1, 2, 11, 22)
    val ts_now = System.currentTimeMillis()
    val ts_before = ts_now - (100l * Utils.MILLIS_TO_DAYS)
    ratings.store(uId1, pId1, 2, ts_now)
    ratings.store(uId1, pId1, 4, ts_now)
    ratings.store(uId1, pId2, 1, ts_before)

    ratings.toDumpSeq should be(Stream(Seq("1", "11", "6.0")))
  }


}
