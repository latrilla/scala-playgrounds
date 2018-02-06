package com.latrilla.aggregates
import org.scalatest._

class IntLookupTest extends FlatSpec with Matchers {
  "An IntLookup" should "create indexes in numerical order" in {
    val lookup: IntLookup[String] = new IntLookup[String]()
    lookup.store("test") should be (0)
    lookup.store("other test") should be (1)
    lookup.store("last test") should be (2)
  }



  "An IntLookup" should "not create two index for the same object" in {
    val lookup: IntLookup[String] = new IntLookup[String]()
    lookup.store("test") should be (0)
    lookup.store("test") should be (0)
  }


  "An IntLookup" should "return the index of an object" in {
    val lookup: IntLookup[String] = new IntLookup[String]()
    lookup.store("test") should be (0)
    lookup.store("test 2") should be (1)
    lookup.get("test") should be (Some(0))
  }

  "An IntLookup" should "return a dump seq with two values each time" in {
    val lookup: IntLookup[String] = new IntLookup[String]()
    lookup.store("test") should be (0)
    lookup.store("test 2") should be (1)

    lookup.toWriteSeq should be (Seq(Seq("test 2", "1"), Seq("test", "0")))
  }
}
