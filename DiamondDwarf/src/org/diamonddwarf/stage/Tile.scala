package org.diamonddwarf.stage

class Tile private (val symbol: String, val isPassable: Boolean) {
  
  override def toString = this.symbol

}

object Tile {
  
  val diggableTile = 	new Tile(".", true)
  val dugTile = 		new Tile("o", true)
  val baseTile = 		new Tile("x", true)
  val stoneTile = 		new Tile("#", false)
  
}