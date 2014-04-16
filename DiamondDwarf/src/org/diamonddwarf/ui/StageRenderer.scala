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
import org.diamonddwarf.ResourceLoader
import com.badlogic.gdx.graphics.g2d.TextureRegion
import scala.util.control.Exception

class StageRenderer(game: DiamondDwarf, private val batch: SpriteBatch, private val rl: ResourceLoader) {
  private var camera: OrthographicCamera = null
  private val font = new BitmapFont

  private val mapXOffset = 20
  private val mapYOffset = 50

  private val inventoryXOffset = 250
  private val inventoryYOffset = 220

  private val tileSize = 64

  private var randomIds: Array[Array[Int]] = null

  private var playerTextureRegion: TextureRegion = null

  font.setColor(Color.BLACK)

  def setNewRandomIds(stage: TileMap) {
    this.randomIds = Array.fill(stage.width, stage.height)(Random.nextInt.abs)
  }

  def create {
    val w = Gdx.graphics.getWidth()
    val h = Gdx.graphics.getHeight()

    camera = new OrthographicCamera(1, h / w)

    playerTextureRegion = this.rl.textureRegionMapForActors.get(game.player) match {
      case Some(region) => region.get(0)
      case _ => null
    }
  }

  def render() {
    // batch.setProjectionMatrix(camera.combined);
    batch.begin()
    this.renderTiles
    batch.end()
  }

  def dispose = {

  }

  private def renderTiles {
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

      batch.end()
      batch.begin()

    }

    val (lerpx, lerpy) = actorDrawPosition(game.player, game.activeMap.playerPosition.x, game.activeMap.playerPosition.y)
    val textureRegion = game.player.getTextureRegion
    if (textureRegion != null)

      batch.draw(game.player.getTextureRegion, lerpx, lerpy)

  }

  private def actorDrawPosition(a: Actor, x: Int, y: Int) = {
    var percent = 1.0f
    val state = this.game.player.states.activeState
    if (state == this.game.player.states.moving)
        percent = state.progress / state.speed
  
    
    (lerp((x - a.direction.x) * tileSize, x * tileSize, percent),
          lerp((y - a.direction.y) * tileSize, y * tileSize, percent))
  }
  private def lerp(start: Float, end: Float, percent: Float) = start + percent * (end - start)

  private def getTextureFromSpriteMap(gameObj: GameObject): TextureRegion = {
    this.rl.textureRegionMapForVariants.get(gameObj) match {
      case Some(textures) =>
        val rand = Random.nextInt(textures.size)
        return textures.get(rand)
      case _ =>
        return null
    }
  }

  private def getTextureOf(obj: GameObject, x: Int, y: Int): Option[TextureRegion] = {
    this.rl.textureRegionMapForVariants.get(obj) match {
      case Some(textures) =>
        if (textures.size == 0) throw new Exception("No textures found for " + obj.toString())
        Some(textures.get(this.randomIds(x)(y) % textures.size))
      case _ => None
    }
  }
}