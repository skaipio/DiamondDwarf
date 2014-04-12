package org.diamonddwarf.stage

class Tile private (val symbol: String) extends GameObject {
  
  override def toString = this.symbol

}

object Tile {
  
  val diggableTile = 	new Tile(".")
  val baseTile = 		new Tile("x")
  
}