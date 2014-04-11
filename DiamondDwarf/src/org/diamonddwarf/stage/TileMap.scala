package org.diamonddwarf.stage

abstract class TileMap(val width: Int, val height: Int) extends Traversable[Tile] {
  private val tileMap: Array[Array[Tile]] = Array.fill(height, width)(Tile.newEmptyTile)
  def getTileAt(x: Int, y: Int) = tileMap(y)(x)
  def getTileAt(coordinate: Coordinate): Tile = getTileAt(coordinate.x, coordinate.y)
  def setTileAt(x: Int, y: Int, t: Tile) = tileMap(y)(x) = t
  def setTileAt(coordinate: Coordinate, t: Tile): Unit = this.setTileAt(coordinate.x, coordinate.y, t)
  def inBounds(x: Int, y: Int) = x >= 0 && y >= 0 && x < this.width && y < this.height
  def inBounds(coordinate: Coordinate): Boolean = this.inBounds(coordinate.x, coordinate.y)

  var playerPosition = Coordinate.Zero
  def setPlayerPosition(coordinate: Coordinate) { this.playerPosition = coordinate }
  def setPlayerPosition(x: Int, y: Int) { this.playerPosition = new Coordinate(x, y) }

  def playerTile = this.getTileAt(playerPosition.x, playerPosition.y)
  def isPlayerAt(t: Tile) = t == playerTile
  def isPlayerAt(x: Int, y: Int): Boolean = this.playerPosition.x == x && this.playerPosition.y == y

  override def foreach[U](f: Tile => U) = {
    for (row <- tileMap; tile <- row) f(tile)
  }

  // Whole map as symbols
  override def toString = {
    var returning = new StringBuilder()
    for (row <- this.tileMap.reverse) {
      for (t <- row) {
        if (t == playerTile) returning += '@'
        else returning ++= t.toString
      }
      returning += '\n'
    }
    returning.toString
  }
}