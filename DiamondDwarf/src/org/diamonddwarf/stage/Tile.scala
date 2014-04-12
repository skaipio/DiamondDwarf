package org.diamonddwarf.stage

import org.diamonddwarf.items.Gem

class Tile private (val symbol: String) {
  protected var isPassable = true
  protected var gem = Gem.noGem

  def passable = this.isPassable

  private var dug = false

  def dig = {
    this.dug = true
    val dugGem = this.gem
    this.gem = Gem.noGem
    dugGem
  }

  def hasGem = this.gem != Gem.noGem
  def isDug = this.dug

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
    new Tile("x")
  }
}