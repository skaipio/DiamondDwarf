package org.diamonddwarf.actors

import org.diamonddwarf.tileobjects.TileObject
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import fs.tileboard.board.Trackable
import fs.tileboard.board.CollisionGroupable
import com.badlogic.gdx.graphics.g2d.Batch
import org.diamonddwarf.Game

class TileObjectActor(val tileObject: TileObject, defaultTexture: Option[AtlasRegion])
  extends DDActor with CollisionGroupable with Trackable {

  var position: () => Option[(Int, Int, Int)] = _

  val collisionGroup = this.tileObject.collisionGroup

  this.texture = defaultTexture

  override def drawingPosition = {
    require(position != null && position().isDefined, "No position method has been assigned for tile object " + this)
    val (tileX, tileY, _) = this.position().get
    (tileX * Game.tilesize, tileY * Game.tilesize)
  }

  override def update(delta: Float) = {}

  def getLayer = tileObject.layer

  override def toString = this.tileObject.toString
}