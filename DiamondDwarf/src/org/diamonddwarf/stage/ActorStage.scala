package org.diamonddwarf.stage

import board.CollisionBoard
import board.Zero
import org.diamonddwarf._
import org.diamonddwarf.actors._
import org.diamonddwarf.stage.tileobjects._
import com.badlogic.gdx.scenes.scene2d.Stage
import scala.collection.mutable.Map

class ActorStage(stageTemplate: StageTemplate, actorFactory: ActorFactory) {
  type C3 = (Int, Int, Int)
  type C2 = (Int, Int)

  private val objStage = new TileObjectStage(stageTemplate)
  private val positionMap: Map[DDActor, C2] = Map()
  private val stage: Stage = new Stage

  var buildableIndex = 0
  var currentTime = 0f

  // Initialization
  this.objStage.boardCoordinates(Grass.layer).foreach(c => {
    val grass = actorFactory.createTileObject(Grass, this)
    this.addObject(grass, c)
  })
  // Add player to stage
  this.addObject(actorFactory.createTileObject(Player, this),
    (objStage.stageTemplate.baseX, objStage.stageTemplate.baseY))

  def addObject(obj: DDActor, whereTo: C2) {
    if (!this.stage.getActors().contains(obj, true)) {
      val c = (whereTo._1, whereTo._2, obj.tileObject.layer)
      this.objStage.setObject(obj.tileObject, whereTo)
      this.stage.addActor(obj)
      this.positionMap += (obj -> c)
    }
  }

  def moveObject(obj: DDActor, whereTo: C2) {
    require(this.stage.getActors().contains(obj, true))
    require(this.positionMap.contains(obj))
    val oldPos = this.getPosition(obj).get
    this.objStage.remove(oldPos) // how does this work???
    this.objStage.setObject(obj.tileObject, whereTo)
    this.positionMap += (obj -> whereTo)
  }

  def hasObjectAt(c: C3) = this.objStage.objectAt(c).isDefined

  def getPosition(obj: DDActor) = this.positionMap.get(obj) match {
    case Some(c) => Some((c._1, c._2, obj.tileObject.layer))
    case _ => None
  }

  def draw = stage.draw

  private implicit def tuple3totuple2(c: C3): C2 = (c._1, c._2)
}

