package org.diamonddwarf.ui

import org.diamonddwarf.stage._
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.diamonddwarf.DiamondDwarf
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture.TextureFilter
import scala.util.Random
import com.badlogic.gdx.graphics.OrthographicCamera
import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.graphics.g2d.TextureRegion
import scala.util.control.Exception
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

class StageRenderer(batch: SpriteBatch, seamMap: Map[(Int, Int), AtlasRegion]) {

  private val font = new BitmapFont

  private val mapXOffset = 20
  private val mapYOffset = 50

  private val inventoryXOffset = 250
  private val inventoryYOffset = 220

  private val tileSize = 64

  font.setColor(Color.BLACK)

  def create {
    val w = Gdx.graphics.getWidth()
    val h = Gdx.graphics.getHeight()
  }

  def render() {
    this.renderTiles
    this.renderSeams
    this.renderTileObjects
    this.drawActors
    this.drawEffects
  }

  def dispose = {

  }

  private def renderTiles {
    batch.begin()
    for (y <- 0 until DiamondDwarf.activeMap.height; x <- 0 until DiamondDwarf.activeMap.width) {
      val tile = DiamondDwarf.activeMap.getTileAt(x, y)
      if (tile.getTexture != null) {
        batch.draw(tile.getTexture, x * tileSize, y * tileSize)
      }
    }
    batch.end()
  }

  private def renderTileObjects {
    batch.begin()
    for (y <- 0 until DiamondDwarf.activeMap.height; x <- 0 until DiamondDwarf.activeMap.width) {
      val tileObj = DiamondDwarf.activeMap.getTileObjectAt(x, y)
      val texture = tileObj.getTexture
      if (texture != null)
        batch.draw(texture, x * tileSize, y * tileSize)

    }
    batch.end()
  }

  private def drawActors = {
    batch.begin()
    val (lerpx, lerpy) = actorDrawPosition(DiamondDwarf.player, DiamondDwarf.activeMap.playerPosition.x, DiamondDwarf.activeMap.playerPosition.y)
    val textureRegion = DiamondDwarf.player.getTexture
    if (textureRegion != null)
      batch.draw(DiamondDwarf.player.getTexture, lerpx, lerpy)
    batch.end()
  }

  private def drawEffects {
    val oldColor = batch.getColor()
    batch.begin()
    for (effect <- DiamondDwarf.player.getEffects) {
      batch.setColor(effect.red, effect.green, effect.blue, effect.alpha)
      for ((anim, c) <- effect.animations) {
        val frame = anim.getCurrentFrame
        batch.draw(frame, effect.x + c.x, effect.y + c.y)
      }
    }
    batch.end()
    batch.setColor(oldColor)
  }

  private def renderSeams {
    batch.begin()
    for (y <- 0 until DiamondDwarf.activeMap.height - 1; x <- 0 until DiamondDwarf.activeMap.width - 1) {
      val tile = DiamondDwarf.activeMap.getTileAt(x, y)
      val top = DiamondDwarf.activeMap.getTileAt(x, y + 1)
      val right = DiamondDwarf.activeMap.getTileAt(x + 1, y)
      this.seamMap.get((tile.id, top.id)) match {
        case Some(region) =>
          this.batch.draw(region, x * tileSize + tileSize, y * tileSize + tileSize - 4, 0f, 0f, region.originalWidth, region.originalHeight, 1f, 1f, 90f)
        case _ =>
      }
      this.seamMap.get((top.id, tile.id)) match {
        case Some(region) =>
          this.batch.draw(region, x * tileSize, y * tileSize + tileSize + 4, 0f, 0f, region.originalWidth, region.originalHeight, 1f, 1f, -90f)
        case _ =>
      }
      this.seamMap.get((tile.id, right.id)) match {
        case Some(region) =>
          this.batch.draw(region, x * tileSize + tileSize - 4, y * tileSize, 0f, 0f, region.originalWidth, region.originalHeight, 1f, 1f, 0f)
        case _ =>
      }
      this.seamMap.get((right.id, tile.id)) match {
        case Some(region) =>
          this.batch.draw(region, x * tileSize + tileSize + 4, y * tileSize + tileSize, 0f, 0f, region.originalWidth, region.originalHeight, 1f, 1f, 180f)
        case _ =>
      }
    }
    batch.end
  }

  private def actorDrawPosition(a: Actor, x: Int, y: Int) = {
    var percent = 1.0f
    val state = a.activeState
    if (state == a.states.moving)
      percent = state.progress / state.speed

    (lerp((x - a.direction.x) * tileSize, x * tileSize, percent),
      lerp((y - a.direction.y) * tileSize, y * tileSize, percent))
  }

  private def lerp(start: Float, end: Float, percent: Float) = start + percent * (end - start)
}