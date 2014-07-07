package org.diamonddwarf.actors

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

abstract class DDActor {
  protected[this] var texture: Option[AtlasRegion] = None
  protected[this] var isVisible = true

  def drawingPosition(): (Float, Float)
  def update(delta: Float): Unit
  def draw(batch: Batch) = if (isVisible) {
    val (x, y) = this.drawingPosition
    texture.foreach(batch.draw(_, x, y))
  }
}