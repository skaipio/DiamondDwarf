package org.diamonddwarf.stage

import scala.collection.mutable.Map

class Player(val name: String) {

  var money = 0
  
  val inventory = Map[Gem, Int]()
  
  def give(gem: Gem) {
    if (gem == Gem.noGem) return
    val oldGemCount = this.inventory.get(gem) match {
      case Some(oldGemCount) => this.inventory += gem -> (1 + oldGemCount)
      case None => this.inventory += gem -> 1}
  }
  
  /**
   * Total sum of gem values in the inventory.
   */
  def totalValue = inventory.foldLeft(0)((sum, kv) => sum + kv._1.value * kv._2)
  
  def printInv { println(inventory)}
  
  override def toString = "Player: " + this.name 
  
}