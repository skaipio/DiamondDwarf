package org.diamonddwarf.stage

import scala.Array.canBuildFrom
import scala.collection.mutable.Set
import scala.collection.mutable.IndexedSeq
import scala.collection.Iterable
import scala.util.Random
import org.diamonddwarf.items.Gem
import org.diamonddwarf.stage._
import org.diamonddwarf.factories.TileFactory
import man.Man

class Stage(width: Int, height: Int, tileFactory: TileFactory, stones: Int, gems: Map[Gem, Int], 
    basePosition: Coordinate, val timelimit: Int, val buildables: Array[Workshop]) extends TileMap(width, height, tileFactory) {
  
  require(stones < width * height, "Not enough tiles for " + stones + " stones.")
  require(this.minTilesFromBorders(basePosition.x, basePosition.y, 1), "Not enough room for base at " + basePosition)

  val baseLayer = 0
  
  var buildableIndex = 0
  var currentTime = 0f
  
  this.setBaseAround(basePosition)

  {
    var coordinatePairs = (0 until height).flatMap(y => (0 until width).map(x => (x, y)))
    coordinatePairs = coordinatePairs.filter(c => this.objectAt(c._1, c._2, baseLayer) != TileObject.base)
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
      this.setTileObjectAt(x, y, TileObject.stone)
      coordinatePairs = coordinatePairs.tail
    }
  }

  this.setPlayerPosition(basePosition)

  def hasWorkshopAt(c: Coordinate) = this.getTileObjectAt(c) match {
    case _: Workshop => true
    case _ => false
  }

  def getWorkshopAt(c: Coordinate) = this.getTileObjectAt(c) match {
    case w: Workshop => w
    case _ => null
  }

  def hasBaseAt(c: Coordinate) = this.objectAt(c.x ,c.y,baseLayer ) != Man.nothing

  def setBaseAround(c: Coordinate) {
    if (this.minTilesFromBorders(c.x, c.y, 1)) {
      for (x <- c.x - 1 to c.x + 1; y <- c.y - 1 to c.y + 1) {
        this.setTileAt(x, y, tileFactory.createBaseTile)
      }

    }

  }

  def isAtBorder(c: Coordinate) = {
    c.x <= 1
  }

}


