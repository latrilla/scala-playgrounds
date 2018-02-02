package com.latrilla.aggregates

class IntLookup[K] extends Dumpable {
  private var storage: Map[K, Int] = Map()

  def store(item: K): Int = {
    if (!storage.contains(item)) {
      val s: Int = storage.size
      storage += (item -> s)
      s
    } else {
      storage(item)
    }
  }

  def get(item: K): Option[Int] = storage.get(item)

  override def toDumpSeq: Seq[Seq[String]] = storage.toSeq.map(tu => Seq(tu._1.toString, tu._2.toString))
}
