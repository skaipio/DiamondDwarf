package org.diamonddwarf.boards

import org.diamonddwarf.actors.DDActor
import org.diamonddwarf.tileobjects.CollisionGroups
import scala.collection.mutable.Map
import fs.tileboard.board.ObjectTracker
import fs.tileboard.board.CollisionBoard
import org.diamonddwarf.actors.DDActor
import org.diamonddwarf.actors.DDActor

class ActorBoard(val width: Int, val height: Int, val depth: Int) {
  type C = (Int, Int, Int)
  type C2 = (Int, Int)
  private val board = new CollisionBoard[DDActor](width, height, depth, CollisionGroups.collisionSet) with ObjectTracker[DDActor]

  def getPosition(obj: DDActor) = board.positionOf(obj)

  def add(actor: DDActor, position: C2) {
    val pos = (position._1, position._2, actor.getLayer)
    this.board.spawn(actor, pos)
  }

  def remove(actor: DDActor) = this.getPosition(actor) match {
    case Some(c) => this.board.remove(c)
    case _ =>
  }

  def hasActorAt(c: C) = this.board.objectAt(c).isDefined
  def getActorAt(c: C) = this.board.objectAt(c)

  def move(actor: DDActor, to: C2) = this.board.move(actor, (to._1, to._2, actor.getLayer))
  def moveBy(actor: DDActor, by: C2) = this.board.moveBy(actor, (by._1, by._2, 0))

  def fill(factory: () => DDActor) = {
    val actor = factory()
    this.board.fill(factory, actor.getLayer)
  }

  def subregion(from: C, to: C) = this.board.subregion(from, to)

  def fullboard(layer: Int) = this.board.boardCoordinates(layer)
}