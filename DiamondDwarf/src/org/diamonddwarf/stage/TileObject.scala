package org.diamonddwarf.stage

class TileObject private (val symbol: String, val isPassable: Boolean) extends GameObject {
  
  override def toString = this.symbol

}

object TileObject {
  
  val hole = new TileObject("O", true)
  val stone = 		new TileObject("#", false)
  val empty = new TileObject(" ", true)
  
}