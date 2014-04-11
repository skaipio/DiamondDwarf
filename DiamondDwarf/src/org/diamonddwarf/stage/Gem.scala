package org.diamonddwarf.stage

class Gem(val value: Int, val name: String) {
  override def toString = this.name + " of value " + this.value.toString
}

object Gem {
  def apply(value: Int, name: String) = new Gem(value, name)

  val noGem = Gem(0, "No gem")
  val okayGem = Gem(50, "Okay gem")
  val goodGem = Gem(100, "Good gem")
  val fineGem = Gem(150, "Fine gem")
  val epicGem = Gem(200, "Epic gem")
  val bestGem = Gem(250, "Best gem")
}