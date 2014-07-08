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
import fs.tileboard.board.Board.tuple2asTuple3
import fs.tileboard.board.Board.C

class BoardController(board: ActorBoard, actorFactory: ActorFactory) {

  type C2 = (Int, Int)

  val player = actorFactory.createPlayer

  private def setPositionMethodToActor(a: TileObjectActor) = a.position = () => this.board.positionOf(a)

  // Initialization
  this.setActorTo(player, board.base._1, board.base._2) // set player on board
  this.applyToActorsOnBoard(setPositionMethodToActor)

  protected[this] def movePlayer(direction: C2) = {
    if (this.player.direction == Stay) {
      board.moveBy(player, direction).foreach(c => player.setMoving(direction))
      player.setState(Moving())
      if (direction == Left || direction == Right)
        this.player.facing = direction
    }
  }

  private[this] def setActorTo(a: TileObjectActor, x: Int, y: Int, force: Boolean = true) =
    if (force) this.board.spawn(a, (x, y, a.getLayer))
    else this.board.trySpawn(a, (x, y, a.getLayer))

  private[this] def applyToActorsOnBoard(f: TileObjectActor => _) =
    board.boardObjects.map(_.map(f))

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

  def update(delta: Float) = board.boardObjects.foreach(_.foreach(_.update(delta)))

  def draw(batch: Batch) = {
    batch.begin()
    board.boardObjects.foreach(_.foreach(_.draw(batch)))
    batch.end()
  }

  //  private def requireActorOnBoard(actor: DDActor) =
  //    require(actorExistsOnBoard(actor), "Actor " + actor + " not found on board.")

  //  private def actorExistsOnBoard(actor: DDActor) =
  //    this.layerGroups(actor.getLayer).getChildren().contains(actor, true) && this.getPosition(actor).isDefined
}

abstract class Direction(x: Int, y: Int) extends Tuple2(x, y)

object Stay extends Direction(0, 0)
object Up extends Direction(0, 1)
object Down extends Direction(0, -1)
object Left extends Direction(-1, 0)
object Right extends Direction(1, 0)

