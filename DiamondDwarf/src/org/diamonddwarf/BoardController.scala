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

class BoardController(board: ActorBoard, actorFactory: ActorFactory) {
  type C = (Int, Int, Int)
  type C2 = (Int, Int)

  private val stage: DDStage = new DDStage(this, new ActionFactory(actorFactory, this))
  private var player: DDActor = null

  var buildableIndex = 0
  var currentTime = 0f

  // Initialization
  Gdx.input.setInputProcessor(stage)
  // Add actors from board to stage
  (0 until board.depth).foreach(board.fullboard(_).foreach(board.getActorAt(_).foreach(actor => {
    this.stage.addActor(actor)
    actor.position = () => this.board.getPosition(actor)
    if (actor.tileObject == Player) player = actor
  })))

  def getPlayerActor = player

  def getPosition(actor: DDActor) = board.getPosition(actor).asInstanceOf[Option[C]]

  def addActor(actor: DDActor, whereTo: C2) = {
    if (!this.stage.getActors().contains(actor, true)) {
      val c = (whereTo._1, whereTo._2, actor.getLayer)
      this.stage.addActor(actor)
      this.board.add(actor, whereTo)
      actor.position = () => this.board.getPosition(actor)
      true
    } else false
  }

  def moveActor(actor: DDActor, whereTo: C2) = {
    this.requireActorOnBoard(actor)
    this.board.move(actor, whereTo)
  }

  def moveActor(actor: DDActor, by: Direction) = {
    this.requireActorOnBoard(actor)
    this.board.moveBy(actor, by)
  }

  def hasActorAt(c: C) = this.board.hasActorAt(c)

  def update = stage.act

  def draw = stage.draw

  private def requireActorOnBoard(actor: DDActor) =
    require(actorExistsOnBoard(actor), "Actor " + actor + " not found on board.")

  private def actorExistsOnBoard(actor: DDActor) =
    this.stage.getActors().contains(actor, true) && this.getPosition(actor).isDefined
}

abstract class Direction(x: Int, y: Int) extends Tuple2(x, y)

object Up extends Direction(0, 1)
object Down extends Direction(0, -1)
object Left extends Direction(-1, 0)
object Right extends Direction(1, 0)

