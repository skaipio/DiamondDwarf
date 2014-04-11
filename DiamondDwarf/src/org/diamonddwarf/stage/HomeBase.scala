package org.diamonddwarf.stage

final class HomeBase(width: Int, height: Int) extends TileMap(width, height){
  this.setTileAt(width/2, height-1, Tile.newExitFromBase)
}