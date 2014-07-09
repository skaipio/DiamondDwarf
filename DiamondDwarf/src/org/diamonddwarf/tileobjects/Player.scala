package org.diamonddwarf.tileobjects

import scala.collection.mutable.Map

object Player extends GroundObject {

  var name = ""
  var score = 0
  var shovel: Equipment = Equipment.shabbyShovel

  val inventory = Map[Gem, Int]()

  def give(gem: Gem) {
    val oldCount = this.inventory.get(gem) match {
      case Some(oldCount) => this.inventory += gem -> (1 + oldCount)
      case None => this.inventory += gem -> 1
    }
    updateScore
  }

  def canDig: Boolean = this.shovel != null && this.shovel.usesLeft >= 1

  def setShovelUsages(times: Int) {
    require(times >= 0)
    this.shovel.usesLeft = times
  }
  
  def depleteShovel: Boolean = {
    if (canDig) {
      this.shovel.usesLeft -= 1
      return true
    }
    return false
  }

  /**
   * Total sum of gem values in the inventory.
   */
  private def updateScore { this.score = inventory.foldLeft(0)((sum, kv) => sum + kv._1.value * kv._2) }

  override def toString = "Player: " + this.name

}