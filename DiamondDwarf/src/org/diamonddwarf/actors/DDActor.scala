package org.diamonddwarf.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import org.diamonddwarf.tileobjects.TileObject
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Batch
import org.diamonddwarf.Game
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import org.diamonddwarf.BoardController
import fs.tileboard.board.CollisionGroupable
import org.diamonddwarf.boards.ActorBoard
import com.badlogic.gdx.scenes.scene2d.Action
import scala.collection.JavaConversions
import fs.tileboard.board.Trackable

class DDActor(val tileObject: TileObject, protected val textureRegion: Option[AtlasRegion])
  extends CollisionGroupable with Trackable {

  var position: () => Option[(Int, Int, Int)] = _

  val collisionGroup = this.tileObject.collisionGroup

  def update(delta: Float) {}

  def draw(batch: Batch) {
    if (position != null) (position(), textureRegion) match {
      case (Some(pos), Some(region)) => batch.draw(region, pos._1 * Game.tilesize, pos._2 * Game.tilesize)
      case _ =>
    }
 
  }

  def getLayer = tileObject.layer

  override def toString = this.tileObject.toString
}