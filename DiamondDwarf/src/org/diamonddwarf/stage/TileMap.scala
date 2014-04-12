package org.diamonddwarf.stage

import org.diamonddwarf.items.Gem
import scala.collection.mutable.Map

abstract class TileMap(val width: Int, val height: Int) extends Traversable[Tile] {
  private val tileMap: Array[Array[Tile]] = Array.fill(height, width)(Tile.diggableTile)
  private val gemMap: Map[Coordinate, Gem] = Map()

  def getTileAt(x: Int, y: Int) = tileMap(y)(x)
  def getTileAt(coordinate: Coordinate): Tile = getTileAt(coordinate.x, coordinate.y)

  def setTileAt(x: Int, y: Int, t: Tile) = tileMap(y)(x) = t
  def setTileAt(coordinate: Coordinate, t: Tile): Unit = this.setTileAt(coordinate.x, coordinate.y, t)

  def setGemAt(x: Int, y: Int, gem: Gem) = {
    require(inBounds(x, y))
    this.gemMap += Coordinate(x, y) -> gem
  }

  def removeGemAt(c: Coordinate) = {
    require(inBounds(c))
    val gem = this.gemMap.get(c)
    this.gemMap -= c
    gem
  }

  def inBounds(x: Int, y: Int) = this.minTilesFromBorders(x, y, 0)
  def inBounds(coordinate: Coordinate): Boolean = this.inBounds(coordinate.x, coordinate.y)
  def minTilesFromBorders(x: Int, y: Int, padding: Int) = {
    x >= padding && y >= padding && x < this.width - padding && y < this.height - padding
  }

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