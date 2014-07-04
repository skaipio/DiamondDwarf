package org.diamonddwarf.boards

import org.diamonddwarf.actors._
import org.diamonddwarf.tileobjects._
import fs.tileboard.board.CollisionBoard

class TileObjectBoard(val stageTemplate: BoardTemplate)
  extends CollisionBoard[TileObject](stageTemplate.width, stageTemplate.height, 3, CollisionGroups.collisionSet) {
  type C3 = (Int, Int, Int)
  type C2 = (Int, Int)

  var buildableIndex = 0
  var currentTime = 0f

  // Initialization
//  fill(Grass, Grass.layer)
//  spawn(Player, (stageTemplate.baseX, stageTemplate.baseY, Player.layer))
//  setBaseAround(stageTemplate.baseX, stageTemplate.baseY)

  def setObject(obj: TileObject, whereTo: C2) {
    val c = (whereTo._1, whereTo._2, obj.layer)
    this.spawn(obj, c)
  }

  def hasObjectAt(c: C3) = this.objectAt(c).isDefined

  /*
  {
    var coordinatePairs = (0 until height).flatMap(y => (0 until width).map(x => (x, y)))
    coordinatePairs = coordinatePairs.filter(c => this.objectAt(c._1, c._2, DwarfBase.layer) != Some(DwarfBase))
    coordinatePairs = Random.shuffle(coordinatePairs)

    for (gems_ <- stageTemplate.gemMap) {
      for (_ <- 0 until gems_._2) {
        val (x, y) = coordinatePairs.head

        this.setGemAt(x, y, gems_._1)
        coordinatePairs = coordinatePairs.tail
      }
    }

    for (_ <- 0 until stageTemplate.stones) {
      val (x, y) = coordinatePairs.head
      this.place(Stone, (x, y, Stone.layer))
      coordinatePairs = coordinatePairs.tail
    }
  }*/

  /*  def hasWorkshopAt(x: Int, y: Int) = this.objectAt(x, y, Workshop.layer) match {
    case Some(w: Workshop) => true
    case _ => false
  }

  def getWorkshopAt(x: Int, y: Int) = this.objectAt(x, y, Workshop.layer) match {
    case Some(w: Workshop) => w
    case _ => None
  }

  def hasBaseAt(x: Int, y: Int) = this.objectAt(x, y, DwarfBase.layer) == DwarfBase

  */

  //  def gemsBetween(a: C, b: C) = board.subregion(a, b).collect(c => this.getGemAt(c._1, c._2) match {
  //    case Some(g: Gem) => g
  //  })
  def tileObjectsBetween(a: C3, b: C3) = for (c <- this.subregion(a, b) if this.inBounds(c)) yield this.objectAt(c)

  /*def setDugAt(x: Int, y: Int) {
    require(board.inBounds((x, y, 0)))
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
  }*/

  def isPlayerAt(x: Int, y: Int): Boolean =
    this.inBounds(x, y, Player.layer) && this.objectAt(x, y, Player.layer).contains(Player)

  private def setBaseAround(x: Int, y: Int) {
    require(this.minTilesFromBorders(x, y, 1))
    subregion((x - 1, y - 1, DwarfBase.layer), (x + 1, y + 1, DwarfBase.layer)).foreach(spawn(DwarfBase, _))
  }

  private def minTilesFromBorders(x: Int, y: Int, padding: Int) = {
    x >= padding && y >= padding && x < this.width - padding && y < this.height - padding
  }

  private implicit def tuple3totuple2(c: C3): C2 = (c._1, c._2)
}

