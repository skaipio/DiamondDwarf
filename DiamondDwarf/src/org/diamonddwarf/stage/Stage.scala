package org.diamonddwarf.stage

import scala.Array.canBuildFrom
import scala.collection.mutable.IndexedSeq
import scala.collection.Iterable
import scala.util.Random
import org.diamonddwarf.stage._
import org.diamonddwarf.factories.TileFactory
import org.diamonddwarf.stage.tileobjects._
import board.CollisionBoard
import org.diamonddwarf.stage.tileobjects.Hole

class Stage(width: Int, height: Int, tileFactory: TileFactory, stones: Int, gems: Map[Gem, Int],
  basePosition: Coordinate, val timelimit: Int, val buildables: Array[Workshop]) extends CollisionBoard[TileObject](width, height, 3, Set((1, Set(2)))) {

  require(stones < width * height, "Not enough tiles for " + stones + " stones.")
  require(this.minTilesFromBorders(basePosition.x, basePosition.y, 1), "Not enough room for base at " + basePosition)

  this.fill(Grass, Grass.layer)

  def objectAt(c: Coordinate): Option[TileObject] = this.objectAt(c.x, c.y, c.z)
  def hasTileObjectAt(c: Coordinate) = this.objectAt(c) match {
    case Some(_) => true
    case _ => false
  }
  
  //def tilesBetween(a: Coordinate, b: Coordinate) = for (c <- a to b if inBounds(c)) yield this.tileAt(c.x, c.y)
  def gemsBetween(a: Coordinate, b: Coordinate) = (a to b).collect(c => this.getGemAt(c.x, c.y) match {
    case Some(g: Gem) => g
  })
  def tileObjectsBetween(a: Coordinate, b: Coordinate) = for (x <- a to b if inBounds(x)) yield this.objectAt(x)

  def setDugAt(x: Int, y: Int) {
    require(this.inBounds((x, y, 0)))
    this.placeForced(Hole, (x, y, Hole.layer))
  }
  def isDug(x: Int, y: Int) = this.objectAt((x, y, Hole.layer)) match {
    case Some(Hole) => true
    case _ => false
  }

  def setGemAt(x: Int, y: Int, gem: Gem) = {
    require(this.inBounds(x, y, 0))
    this.placeForced(gem, (x, y, gem.layer))
  }

  def getGemAt(x: Int, y: Int) = this.objectAt((x, y, Gem.layer))

  def hasGemAt(x: Int, y: Int) = this.objectAt((x, y, Gem.layer)) match {
    case Some(Gem(_, _)) => true
    case _ => false
  }

  def removeGemAt(x: Int, y: Int) = {
    val removeAt = (x, y, Gem.layer)
    require(inBounds(removeAt))
    val gem = this.getGemAt(x, y)
    gem match {
      case Some(_) => this.remove(removeAt)
      case _ =>
    }
    gem
  }

  def minTilesFromBorders(x: Int, y: Int, padding: Int) = {
    x >= padding && y >= padding && x < this.width - padding && y < this.height - padding
  }

  var playerPosition = new Coordinate(0, 0, 0)

  def setPlayerPosition(x: Int, y: Int) { this.playerPosition = new Coordinate(x, y, Player.layer) }
  def isPlayerAt(x: Int, y: Int): Boolean = this.playerPosition.x == x && this.playerPosition.y == y

  var buildableIndex = 0
  var currentTime = 0f

  this.setBaseAround(basePosition.x, basePosition.y)

  {
    var coordinatePairs = (0 until height).flatMap(y => (0 until width).map(x => (x, y)))
    coordinatePairs = coordinatePairs.filter(c => this.objectAt(c._1, c._2, DwarfBase.layer) != Some(DwarfBase))
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
      this.place(Stone, (x, y, Stone.layer))
      coordinatePairs = coordinatePairs.tail
    }
  }

  this.setPlayerPosition(basePosition.x, basePosition.y)

  def hasWorkshopAt(x: Int, y: Int) = this.objectAt(x, y, Workshop.layer) match {
    case Some(w : Workshop) => true
    case _ => false
  }

  def getWorkshopAt(x: Int, y: Int) = this.objectAt(x, y, Workshop.layer) match {
    case Some(w : Workshop) => w
    case _ => None
  }

  def hasBaseAt(x: Int, y: Int) = this.objectAt(x, y, DwarfBase.layer) == DwarfBase

  def setBaseAround(x: Int, y: Int) {
    if (this.minTilesFromBorders(x, y, 1)) {
      for (x <- x - 1 to x + 1; y <- y - 1 to y + 1) {
        this.place(DwarfBase, (x, y, DwarfBase.layer))
      }

    }

  }
}

