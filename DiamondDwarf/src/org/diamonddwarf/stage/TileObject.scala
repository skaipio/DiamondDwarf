package org.diamonddwarf.stage

import man.Man

class TileObject(val isPassable: Boolean) extends Man with Actor

object TileObject {

  val hole = new TileObject(true)
  val stone = new TileObject(false)
  val minedStone = new TileObject(true)
  val empty = new TileObject(true)
  val base = new TileObject(true)

}