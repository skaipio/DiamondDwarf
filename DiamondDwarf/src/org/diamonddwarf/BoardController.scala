package org.diamonddwarf

import org.diamonddwarf.actors._
import org.diamonddwarf.tileobjects._
import scala.collection.mutable.Map
import org.diamonddwarf.boards.TileObjectBoard
import com.badlogic.gdx.Gdx
import org.diamonddwarf.tileobjects.TileObject
import org.diamonddwarf.boards.ActorBoard
import fs.tileboard.board.ObjectTracker
import fs.tileboard.board.CollisionBoard
import org.diamonddwarf.boards.ActorBoard
import com.badlogic.gdx.scenes.scene2d.Group
import fs.tileboard.board.Board
import com.badlogic.gdx.graphics.g2d.Batch

class BoardController(board: ActorBoard, actorFactory: ActorFactory) {

  type C = (Int, Int, Int)
  type C2 = (Int, Int)

  val player = actorFactory.createActorOf(Player)

  private def setPositionMethodToActor(a: DDActor) = a.position = () => this.board.positionOf(player)

  // Initialization
  board.spawn(player, (board.base._1, board.base._2, player.getLayer)) // set player on board
  board.boardCoordinates.foreach(board.objectAt(_).foreach(setPositionMethodToActor))

  //  private def addActorToLayer(actor: DDActor): Boolean = {
  //    this.layerGroups(actor.getLayer).addActor(actor)
  //    actor.position = () => this.board.getPosition(actor)
  //    if (actor.tileObject == Player) player = actor
  //    true
  //  }

  //  def getPosition(actor: DDActor) = board.getPosition(actor).asInstanceOf[Option[C]]

  //  def addActor(actor: DDActor, whereTo: C2): Boolean = {
  //    val newPos = (whereTo._1, whereTo._2, actor.getLayer)
  //    !actorExistsOnBoard(actor) && this.board.add(actor, whereTo) &&
  //      addActorToLayer(actor)
  //  }

  //  def forceAddActor(actor: DDActor, whereTo: C2): Boolean = {
  //    val removed = this.board.removeAt((whereTo._1, whereTo._2, actor.getLayer))
  //    removed.foreach(a => this.layerGroups(a.getLayer).removeActor(a))
  //    this.addActor(actor, whereTo)
  //  }

  //  def moveActor(actor: DDActor, whereTo: C2) = {
  //    this.requireActorOnBoard(actor)
  //    this.board.move(actor, whereTo)
  //  }

  //  def moveActor(actor: DDActor, by: Direction) = {
  //    this.requireActorOnBoard(actor)
  //    this.board.moveBy(actor, by)
  //  }

  //  def removeActor(actor: DDActor) = {
  //    this.board.remove(actor)
  //    this.layerGroups(actor.getLayer).removeActor(actor)
  //  }

  //  def hasActorAt(c: C) = this.board.hasActorAt(c)

  def update(delta: Float) = board.boardCoordinates.foreach(board.objectAt(_).foreach(_.update(delta)))

  def draw(batch: Batch) = {
    batch.begin()
    board.boardCoordinates.foreach(board.objectAt(_).foreach(_.draw(batch)))
    batch.end()
  }

  //  private def requireActorOnBoard(actor: DDActor) =
  //    require(actorExistsOnBoard(actor), "Actor " + actor + " not found on board.")

  //  private def actorExistsOnBoard(actor: DDActor) =
  //    this.layerGroups(actor.getLayer).getChildren().contains(actor, true) && this.getPosition(actor).isDefined
}

abstract class Direction(x: Int, y: Int) extends Tuple2(x, y)

object Up extends Direction(0, 1)
object Down extends Direction(0, -1)
object Left extends Direction(-1, 0)
object Right extends Direction(1, 0)

