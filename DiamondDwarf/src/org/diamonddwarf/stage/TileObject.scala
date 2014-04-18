package org.diamonddwarf.stage

class TileObject(val isPassable: Boolean) extends GameObject

object TileObject {

  val hole = new TileObject(true)
  val stone = new TileObject(false)
  val empty = new TileObject(true)

}