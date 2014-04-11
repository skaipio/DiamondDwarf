package org.diamonddwarf.stage

class Tile private (val symbol: String) {
  protected var _isEntryToBase = false
  protected var _isExitFromBase = false
  protected var isPassable = true
  protected var gem = Gem.noGem

  def isEntryToBase = this._isEntryToBase
  def isExitFromBase = this._isExitFromBase
  def passable = this.isPassable

  private var dug = false

  def dig = {
    if (!this.isEntryToBase && !this.isExitFromBase) {
      this.dug = true
      val dugGem = this.gem
      this.gem = Gem.noGem
      dugGem
    }
    this.gem
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
  def newEntryToBase = {
    val t = new Tile("B")
    t._isEntryToBase = true
    t
  }
  def newExitFromBase = {
    val t = new Tile("E")
    t._isExitFromBase = true
    t
  }
  def newTileWithGem(gem: Gem) = {
    val t = new Tile(".")
    t.gem = gem
    t
  }
}