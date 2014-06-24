package org.diamonddwarf.stage

import scala.Array.canBuildFrom
import scala.collection.mutable.IndexedSeq
import scala.collection.Iterable
import scala.util.Random
import org.diamonddwarf.stage._
import org.diamonddwarf.factories.TileFactory
import man.Man
import org.diamonddwarf.stage.tileobjects.TileObject
import org.diamonddwarf.stage.tileobjects.Gem
import org.diamonddwarf.stage.tileobjects.Workshop
import man.NoMan
import org.diamonddwarf.stage.tileobjects.NoObject
import org.diamonddwarf.stage.tileobjects.TileObject
import board.CollisionBoard

class Stage(width: Int, height: Int, tileFactory: TileFactory, stones: Int, gems: Map[Gem, Int], 
    basePosition: Coordinate, val timelimit: Int, val buildables: Array[Workshop]) extends CollisionBoard[TileObject](width, height, 3, Set((1,Set(2)))) {
  
  require(stones < width * height, "Not enough tiles for " + stones + " stones.")
  require(this.minTilesFromBorders(basePosition.x, basePosition.y, 1), "Not enough room for base at " + basePosition)

  this.fill(TileObject.grass , TileObject.grass.layer)
  
  def objectAt(c: Coordinate): TileObject = this.objectAt(c.x, c.y, c.z)  
  def hasTileObjectAt(c: Coordinate) = this.objectAt(c) != NoObject  
  def place(c: Coordinate, layer: Int, t: TileObject): Boolean = this.place(t, this.tileAt(c.x, c.y), layer)
  
  def tilesBetween(a: Coordinate, b: Coordinate) = for (c <- a to b if inBounds(c)) yield this.tileAt(c.x ,c.y)
  def gemsBetween(a: Coordinate, b: Coordinate) = for (x <- a to b if inBounds(x) && hasGemAt(x)) yield this.getGemAt(x)
  def tileObjectsBetween(a: Coordinate, b: Coordinate) = for (x <- a to b if inBounds(x)) yield this.objectAt(x)
  
  def setDugAt(c: Coordinate) {
    require(this.inBounds(c))
    this.place(c, TileObject.hole.layer, TileObject.hole)
  } 
  def isDug(c: Coordinate) = this.objectAt(c) == TileObject.hole
  
  def setGemAt(x: Int, y: Int, gem: Gem) = {
    require(this.inBounds(x, y, 0))
    this.place(gem, tileAt(x, y), gem.layer)
  } 
  def getGemAt(c: Coordinate): Gem = this.objectAt(c) match {
    case gem: Gem => gem
    case _ => Gem.noGem
  }

  def hasGemAt(c: Coordinate) = this.objectAt(c) match {
    case gem: Gem => true
    case _ => false
  }
  
  def removeGemAt(c: Coordinate) = {
    require(inBounds(c))
    val gem = this.getGemAt(c)
    this.place(c, Gem.noGem.layer, NoObject)
    gem
  }
  
  def minTilesFromBorders(x: Int, y: Int, padding: Int) = {
    x >= padding && y >= padding && x < this.width - padding && y < this.height - padding
  }

  var playerPosition = Coordinate.Zero
  
  def setPlayerPosition(coordinate: Coordinate) { this.playerPosition = coordinate }
  def setPlayerPosition(x: Int, y: Int) { this.playerPosition = new Coordinate(x, y) }
  
  def playerTile = this.tileAt(playerPosition.x, playerPosition.y)
  def isPlayerAt(t: Tile) = t == playerTile
  def isPlayerAt(x: Int, y: Int): Boolean = this.playerPosition.x == x && this.playerPosition.y == y
  
  var buildableIndex = 0
  var currentTime = 0f
  
  this.setBaseAround(basePosition)

  {
    var coordinatePairs = (0 until height).flatMap(y => (0 until width).map(x => (x, y)))
    coordinatePairs = coordinatePairs.filter(c => this.objectAt(c._1, c._2, TileObject.base.layer) != TileObject.base)
    coordinatePairs = Random.shuffle(coordinatePairs)

    for (gems_ <- gems) {
      for (_ <- 0 until gems_._2) {
        val (x, y) = coordinatePairs.head

        this.setGemAt(x, y, gems_._1)
        coordinatePairs = coordinatePairs.tail
      }
    }

    for (_ <- 0 until stones) {
      val (x, y) = coordinatePairs.head
      this.place( TileObject.stone, this.tileAt(x, y), TileObject.stone.layer)
      coordinatePairs = coordinatePairs.tail
    }
  }

  this.setPlayerPosition(basePosition)

  def hasWorkshopAt(c: Coordinate) = this.objectAt(c.x,c.y,Workshop.layer) match {
    case _: Workshop => true
    case _ => false
  }

  def getWorkshopAt(c: Coordinate) = this.objectAt(c.x,c.y,Workshop.layer) match {
    case w: Workshop => w
    case _ => NoObject
  }

  def hasBaseAt(c: Coordinate) = this.objectAt(c.x ,c.y, TileObject.base.layer) == TileObject.base

  def setBaseAround(c: Coordinate) {
    if (this.minTilesFromBorders(c.x, c.y, 1)) {
      for (x <- c.x - 1 to c.x + 1; y <- c.y - 1 to c.y + 1) {
        this.place(TileObject.base, tileAt(x, y), TileObject.base.layer)
      }

    }

  }

  def isAtBorder(c: Coordinate) = {
    c.x <= 1
  }

}


