package org.diamonddwarf.actors

import com.badlogic.gdx.scenes.scene2d.Actor
import org.diamonddwarf.stage.tileobjects.TileObject
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.Batch
import org.diamonddwarf.stage.TileObjectStage
import org.diamonddwarf.Game
import board.CollisionGroupable
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import org.diamonddwarf.stage.ActorStage

class DDActor(val tileObject: TileObject, val stage: ActorStage, textureRegion: Option[AtlasRegion])
  extends Actor with CollisionGroupable {
  val collisionGroup = this.tileObject.collisionGroup

  override def draw(batch: Batch, parentAlpha: Float) {
    textureRegion.foreach(r => stage.getPosition(this).foreach(c => batch.draw(r, c._1 * Game.tilesize, c._2 * Game.tilesize)))
  }
}