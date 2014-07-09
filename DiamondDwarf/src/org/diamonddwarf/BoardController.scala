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
    if (this.player.direction == Stay && (this.player.getState == Idle() || this.player.getState == Moving())) {
      board.moveBy(player, direction).foreach(c => player.setMoving(direction))
      player.setState(Moving())
      if (direction == Left || direction == Right)
        this.player.facing = direction
    }
  }

  protected[this] def playerDig {
    val playerPos = positionOf(player)
    if (player.getState == Idle() && Player.canDig && forallObjectsAt(playerPos, _.tileObject.diggable)) {
      this.setActorTo(actorFactory.createActorOf(Hole), playerPos._1, playerPos._2, true)
      Player.depleteShovel
      player.setState(Digging())
    }
  }

  private[this] def positionOf(a: TileObjectActor) = board.positionOf(a) match {
    case Some((x, y, z)) => (x, y)
    case _ => throw new Exception("Actor " + a + " not found on board.")
  }

  private[this] def forallObjectsAt(c: C2, p: TileObjectActor => Boolean): Boolean = {
    for (layer <- 0 until board.depth) {
      if (!board.objectAt(c._1, c._2, layer).forall(p))
        return false
    }
    true
  }

  private[this] def setActorTo(a: TileObjectActor, x: Int, y: Int, force: Boolean = true) {
    a.position = () => board.positionOf(a)
    if (force) this.board.spawn(a, (x, y, a.getLayer))
    else this.board.trySpawn(a, (x, y, a.getLayer))
  }

  private[this] def applyToActorsOnBoard(f: TileObjectActor => _) =
    board.boardObjects.map(_.map(f))

  //  def getPosition(actor: DDActor) = board.getPosition(actor).asInstanceOf[Option[C]]

  //  def moveActor(actor: DDActor, whereTo: C2) = {
  //    this.requireActorOnBoard(actor)
  //    this.board.move(actor, whereTo)
  //  }

  //  def moveActor(actor: DDActor, by: Direction) = {
  //    this.requireActorOnBoard(actor)
  //    this.board.moveBy(actor, by)
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

