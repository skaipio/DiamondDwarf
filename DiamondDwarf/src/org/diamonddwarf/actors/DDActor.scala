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

class DDActor(val tileObject: TileObject, textureRegion: Option[AtlasRegion])
  extends Actor with CollisionGroupable {
  
  var position : () => Option[(Int, Int, Int)] = _
  
  val collisionGroup = this.tileObject.collisionGroup

  override def draw(batch: Batch, parentAlpha: Float) {
    if (position != null)
    	textureRegion.foreach(r => position().foreach(c => batch.draw(r, c._1 * Game.tilesize, c._2 * Game.tilesize)))
  }

  def getLayer = tileObject.layer
}