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

class StageRenderer(game: DiamondDwarf, private val batch: SpriteBatch,
  private val textureRegionMap: Map[GameObject, com.badlogic.gdx.utils.Array[AtlasRegion]],
  private val seamMap: Map[(Tile, Tile), AtlasRegion]) {
  private val font = new BitmapFont

  private val mapXOffset = 20
  private val mapYOffset = 50

  private val inventoryXOffset = 250
  private val inventoryYOffset = 220

  private val tileSize = 64

  private var randomIds: Array[Array[Int]] = null

  font.setColor(Color.BLACK)

  def setNewRandomIds(stage: TileMap) {
    this.randomIds = Array.fill(stage.width, stage.height)(Random.nextInt.abs)
  }

  def create {
    val w = Gdx.graphics.getWidth()
    val h = Gdx.graphics.getHeight()
  }

  def render() {
    batch.setColor(1f, 1f, 1f, 1f)
    this.renderTiles
    this.drawSeams
    this.drawActors
    this.drawEffects
  }

  def dispose = {

  }

  private def renderTiles {
    batch.begin()
    for (y <- 0 until game.activeMap.height; x <- 0 until game.activeMap.width) {
      val tile = this.game.activeMap.getTileAt(x, y)
      this.getTextureOf(tile, x, y) match {
        case Some(texture) => batch.draw(texture, x * tileSize, y * tileSize)
        case _ =>
      }
      val tileObj = this.game.activeMap.getTileObjectAt(x, y)
      this.getTextureOf(tileObj, x, y) match {
        case Some(texture) => batch.draw(texture, x * tileSize, y * tileSize)
        case _ =>
      }
    }
    batch.end()
  }

  private def drawActors = {
    batch.begin()
    val (lerpx, lerpy) = actorDrawPosition(game.player, game.activeMap.playerPosition.x, game.activeMap.playerPosition.y)
    val textureRegion = game.player.getTextureRegion
    if (textureRegion != null)
      batch.draw(game.player.getTextureRegion, lerpx, lerpy)
    batch.end()
  }

  private def drawEffects {
    batch.begin()
    for (effect <- game.player.getEffects) {
      batch.setColor(effect.red, effect.green, effect.blue, effect.alpha)
      for ((anim, c) <- effect.animations) {
        val frame = anim.getCurrentFrame
        batch.draw(frame, effect.x + c.x, effect.y + c.y)
      }
    }
    batch.end()
  }

  private def drawSeams {
    batch.begin()
    for (y <- 0 until game.activeMap.height - 1; x <- 0 until game.activeMap.width - 1) {
      val tile = this.game.activeMap.getTileAt(x, y)
      val top = this.game.activeMap.getTileAt(x, y+1)
      val right = this.game.activeMap.getTileAt(x+1, y)
      this.seamMap.get((tile, top)) match{
        case Some(region) =>
          this.batch.draw(region, x*tileSize+tileSize, y*tileSize+tileSize-4, 0f, 0f, region.originalWidth, region.originalHeight, 1f, 1f, 90f)
        case _ =>
      }
      this.seamMap.get((top, tile)) match{
        case Some(region) =>          
          this.batch.draw(region, x*tileSize, y*tileSize+tileSize+4, 0f, 0f, region.originalWidth, region.originalHeight, 1f, 1f, -90f)
        case _ =>
      }
      this.seamMap.get((tile, right)) match{
        case Some(region) =>          
          this.batch.draw(region, x*tileSize+tileSize-4, y*tileSize, 0f, 0f, region.originalWidth, region.originalHeight, 1f, 1f, 0f)
        case _ =>
      }
      this.seamMap.get((right, tile)) match{
        case Some(region) =>          
          this.batch.draw(region, x*tileSize+tileSize+4, y*tileSize+tileSize, 0f, 0f, region.originalWidth, region.originalHeight, 1f, 1f, 180f)
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

  private def getTextureOf(obj: GameObject, x: Int, y: Int): Option[TextureRegion] = {
    this.textureRegionMap.get(obj) match {
      case Some(textures) =>
        if (textures.size == 0) throw new Exception("No textures found for " + obj.toString())
        Some(textures.get(this.randomIds(x)(y) % textures.size))
      case _ => None
    }
  }
}