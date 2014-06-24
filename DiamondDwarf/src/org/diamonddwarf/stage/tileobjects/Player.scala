package org.diamonddwarf.stage.tileobjects

import scala.collection.mutable.Map
import org.diamonddwarf.items.Gem
import org.diamonddwarf.items.Equipment
import org.diamonddwarf.stage.Actor

class Player(val name: String) extends Animate with Actor {
  
  var score = 0
  var shovel: Equipment = null

  val inventory = Map[Gem, Int]()

  def give(gem: Gem) {
    if (gem == Gem.noGem) return
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