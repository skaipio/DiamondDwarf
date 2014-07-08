package org.diamonddwarf.actors

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import org.diamonddwarf.Right
import org.diamonddwarf.Left
import org.diamonddwarf.Direction

abstract class DDActor {
  protected[this] var isVisible = true
  var facing: (Int, Int) = Right

  def drawingPosition(): (Float, Float)
  def update(delta: Float): Unit

  def draw(batch: Batch) = if (isVisible)
    this.getTexture.foreach({
      t => val (x, y) = this.drawingPosition; flipTexture(t); batch.draw(t, x, y)
    })

  private[this] def flipTexture(t: AtlasRegion) =
    t.flip(this.facing == Right && !t.isFlipX() || this.facing == Left && t.isFlipX(), false)

  protected[this] def getTexture(): Option[AtlasRegion]
}