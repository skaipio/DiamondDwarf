package org.diamonddwarf.stage

import org.diamonddwarf.ui.Drawable

class Tile(val id : Int, val isDiggable : Boolean, val isBase : Boolean) extends Drawable

object Tile {
  val diggableID = 0
  val baseID = 1
}