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

class StageRenderer(game: DiamondDwarf, private val batch : SpriteBatch, spriteMap: Map[GameObject, Array[Texture]]) {
  private var camera: OrthographicCamera = null
  private val font = new BitmapFont

  private val mapXOffset = 20
  private val mapYOffset = 50

  private val inventoryXOffset = 250
  private val inventoryYOffset = 220

  private val tileSize = 64

  private var randomIds: Array[Array[Int]] = null

  private var playerTexture: Texture = null

  font.setColor(Color.BLACK)

  def setNewRandomIds(stage: TileMap) {
    this.randomIds = Array.fill(stage.width, stage.height)(Random.nextInt.abs)
  }

  def create {
    val w = Gdx.graphics.getWidth()
    val h = Gdx.graphics.getHeight()

    camera = new OrthographicCamera(1, h / w)

    playerTexture = new Texture(Gdx.files.internal("textures/dwarf2.png"))
    playerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
  }

  def render() {
    //    batch.setProjectionMatrix(camera.combined);
    batch.begin()
    this.renderTiles
    batch.end()
  }

  def dispose = {
    playerTexture.dispose()
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
      if (game.activeMap.isPlayerAt(x, y)) {
        val (lerpx, lerpy) = actorDrawPosition(game.player, x, y)
        batch.draw(this.playerTexture, lerpx, lerpy)
      }
    }

  }

  private def actorDrawPosition(a: Actor, x: Int, y: Int) = {
    val percent = this.game.player.progress / this.game.player.speed
   (lerp((x - a.direction.x) * tileSize, x * tileSize, percent), 
       lerp((y - a.direction.y) * tileSize, y * tileSize, percent))

  }
  private def lerp(start: Float, end: Float, percent: Float) = start + percent * (end - start)

  private def getTextureFromSpriteMap(gameObj: GameObject): Texture = {
    this.spriteMap.get(gameObj) match {
      case Some(textures) =>
        val rand = Random.nextInt(textures.size)
        return textures(rand)
      case _ =>
        return null
    }
  }
  
  private def getTextureOf(obj: GameObject, x: Int, y: Int) : Option[Texture] = {
    this.spriteMap.get(obj) match {
      case Some(textures) => Some(textures(this.randomIds(x)(y) % textures.size))
      case _ => None
    }
  }
}