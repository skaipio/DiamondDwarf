package org.diamonddwarf.stage

class TileObject(val isPassable: Boolean) extends Actor

object TileObject {

  val hole = new TileObject(true)
  val stone = new TileObject(false)
  val empty = new TileObject(true)

}