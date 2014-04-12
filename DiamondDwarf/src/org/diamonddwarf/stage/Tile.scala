package org.diamonddwarf.stage

import org.diamonddwarf.items.Gem

class Tile private (val symbol: String) {
  protected var isPassable = true
  protected var gem = Gem.noGem
  protected var isBase = false
  private var dug = false
  
  def passable = this.isPassable



  def dig = {
    this.dug = true
    val dugGem = this.gem
    this.gem = Gem.noGem
    dugGem
  }

  def hasGem = this.gem != Gem.noGem
  def isDug = this.dug
  def canBeDug = !isDug && !isBase
  
  override def toString = if (this.dug) "O" else this.symbol
}

object Tile {
  def newEmptyTile = new Tile(".")
  def newTileWithStone = {
    val t = new Tile("#")
    t.isPassable = false
    t
  }
  def newTileWithGem(gem: Gem) = {
    val t = new Tile(".")
    t.gem = gem
    t
  }

  def newBaseTile = {
    val t = new Tile("x")
    t.isBase = true
    t
  }
}