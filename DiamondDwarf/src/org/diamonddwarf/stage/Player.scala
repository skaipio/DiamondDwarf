package org.diamonddwarf.stage

import scala.collection.mutable.Map
import org.diamonddwarf.items.Gem
import org.diamonddwarf.items.Shovel

class Player(val name: String) extends Actor(100) {
  var money = 0
  var shovel: Shovel = null
  val inventory = Map[Gem, Int]()

  def give(gem: Gem) {
    if (gem == Gem.noGem) return
    val oldCount = this.inventory.get(gem) match {
      case Some(oldCount) => this.inventory += gem -> (1 + oldCount)
      case None => this.inventory += gem -> 1
    }
  }

  def canDig: Boolean = this.shovel != null && this.shovel.digsLeft >= 1

  def depleteShovel: Boolean = {
    if (canDig) {
      this.shovel.digsLeft -= 1
      return true
    }
    return false
  }

  /**
   * Total sum of gem values in the inventory.
   */
  def totalValue = inventory.foldLeft(0)((sum, kv) => sum + kv._1.value * kv._2)

  override def toString = "Player: " + this.name

}