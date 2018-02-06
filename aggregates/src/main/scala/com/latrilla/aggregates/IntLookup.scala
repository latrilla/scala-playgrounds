package com.latrilla.aggregates

import scala.collection.mutable

/**
  * Lookup object storing items and associating them successive integers
  *
  * @param storage a map containing the... mappings
  * @tparam K the type of your base object
  */
class IntLookup[K](val storage: mutable.Map[K, Int] = mutable.Map[K, Int]()) extends Writable {


  private def nextIndex: Int = storage.size

  /**
    * Store an item and returns its index. Only returns the index if the item is already present
    *
    * @param item the object to store
    * @return the integer lookup value of this object
    */
  def store(item: K): Int = {
    storage get item match {
      case Some(index: Int) => index
      case None =>
        val index: Int = nextIndex
        storage += (item -> index)
        index
    }
  }

  def get(item: K): Option[Int] = storage get item

  override def toWriteSeq: Seq[Seq[String]] = storage.toSeq.map(tu => Seq(tu._1.toString, tu._2.toString))
}
