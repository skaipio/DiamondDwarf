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

class StageRenderer(game: DiamondDwarf, spriteMap: Map[GameObject, Array[Texture]]) {
  private var camera: OrthographicCamera = null
  private val batch = new SpriteBatch
  private val font = new BitmapFont

  private val mapXOffset = 20
  private val mapYOffset = 50

  private val inventoryXOffset = 250
  private val inventoryYOffset = 220

  private val tileSize = 64

  private var randomIds : Array[Array[Int]] = null

  private var playerTexture: Texture = null

  font.setColor(Color.BLACK)
  
  def setNewRandomIds(stage: TileMap){
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
    this.renderPlayerInventory
    batch.end()
  }

  def dispose = {
    batch.dispose()
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

      if (game.activeMap.isPlayerAt(x, y)) {
        batch.draw(this.playerTexture, x * tileSize, y * tileSize)
      }
    }

  }

  private def getTextureFromSpriteMap(gameObj: GameObject): Texture = {
    this.spriteMap.get(gameObj) match {
      case Some(textures) =>
        val rand = Random.nextInt(textures.size)
        return textures(rand)
      case _ =>
        return null
    }
  }

  private def renderPlayerInventory {
    var i = 1
    var additionalXOffset = 0
    var additionalYOffset = -20
    font.draw(batch, game.player.name + "'s inventory", inventoryXOffset, inventoryYOffset)
    if (game.player.shovel != null) {
      font.draw(batch, game.player.shovel.toString, inventoryXOffset, inventoryYOffset + additionalYOffset)
      additionalYOffset -= 20
    }
    for ((gem, count) <- game.player.inventory) {
      font.draw(batch, gem + ": " + count, inventoryXOffset, inventoryYOffset + additionalYOffset - i * 20)
      i += 1
    }
  }
  
  private def getTextureOf(obj: GameObject, x: Int, y: Int) : Option[Texture] = {
    this.spriteMap.get(obj) match {
      case Some(textures) => Some(textures(this.randomIds(x)(y) % textures.size))
      case _ => None
    }
  }
}