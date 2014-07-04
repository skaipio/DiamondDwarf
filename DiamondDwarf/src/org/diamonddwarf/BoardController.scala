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

class BoardController(board: ActorBoard, actorFactory: ActorFactory) {
  type C = (Int, Int, Int)
  type C2 = (Int, Int)

  private val layerGroups = Array.fill(3)(new Group())
  private val stage: DDStage = new DDStage(this, new ActionFactory(actorFactory, this))
  private var player: DDActor = null

  var buildableIndex = 0
  var currentTime = 0f

  // Initialization
  Gdx.input.setInputProcessor(stage)
  // Add groups to stage
  layerGroups.foreach(stage.addActor(_))
  // Add actors from board to stage
  (0 until board.depth).foreach(board.fullboard(_).foreach(board.getActorAt(_).foreach(actor => {
    this.layerGroups(actor.getLayer).addActor(actor)
    actor.position = () => this.board.getPosition(actor)
    if (actor.tileObject == Player) player = actor
  })))

  def getPlayerActor = player

  def getPosition(actor: DDActor) = board.getPosition(actor).asInstanceOf[Option[C]]

  def addActor(actor: DDActor, whereTo: C2): Boolean = {
    var added = false
    val newPos = (whereTo._1, whereTo._2, actor.getLayer)
    if (!actorExistsOnBoard(actor) && !this.board.hasActorAt((newPos))) {
      val actorAtC = this.board.getActorAt(newPos)
      if (this.board.add(actor, whereTo)) {
        actorAtC.foreach(this.removeActor(_))
        this.layerGroups(actor.getLayer).addActor(actor)
        actor.position = () => this.board.getPosition(actor)
        added = true
      }
    }
    added
  }
  
  def forceAddActor(actor: DDActor, whereTo: C2): Boolean = {
    val removed = this.board.removeAt((whereTo._1 ,whereTo._2 ,actor.getLayer))
    removed.foreach(a => this.layerGroups(a.getLayer).removeActor(a))
    this.addActor(actor, whereTo)
  }

  def moveActor(actor: DDActor, whereTo: C2) = {
    this.requireActorOnBoard(actor)
    this.board.move(actor, whereTo)
  }

  def moveActor(actor: DDActor, by: Direction) = {
    this.requireActorOnBoard(actor)
    this.board.moveBy(actor, by)
  }

  def removeActor(actor: DDActor) = {
    this.board.remove(actor)
    this.layerGroups(actor.getLayer).removeActor(actor)
  }

  def hasActorAt(c: C) = this.board.hasActorAt(c)

  def update = stage.act

  def draw = stage.draw

  private def requireActorOnBoard(actor: DDActor) =
    require(actorExistsOnBoard(actor), "Actor " + actor + " not found on board.")

  private def actorExistsOnBoard(actor: DDActor) =
    this.layerGroups(actor.getLayer).getChildren().contains(actor, true) && this.getPosition(actor).isDefined
}

abstract class Direction(x: Int, y: Int) extends Tuple2(x, y)

object Up extends Direction(0, 1)
object Down extends Direction(0, -1)
object Left extends Direction(-1, 0)
object Right extends Direction(1, 0)

