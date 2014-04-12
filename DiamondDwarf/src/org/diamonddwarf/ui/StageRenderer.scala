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

class StageRenderer(game: DiamondDwarf, spriteMap: Map[Tile, Texture]) {
  private val batch = new SpriteBatch
  private val font = new BitmapFont

  private val mapXOffset = 20
  private val mapYOffset = 50

  private val inventoryXOffset = 250
  private val inventoryYOffset = 220

  private val tileSize = 64

  private var playerTexture: Texture = null

  font.setColor(Color.BLACK)

  def create {
    playerTexture = new Texture(Gdx.files.internal("textures/dwarf2.png"))
    playerTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
  }

  def render() {
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
      font.draw(batch, game.activeMap.getTileAt(x, y).toString, x * tileSize + mapXOffset, y * tileSize + mapYOffset)
      this.spriteMap.get(game.activeMap.getTileAt(x, y)) match {
        case Some(sprite) => batch.draw(sprite, x * tileSize, y * tileSize)
        case _ =>
      }
      if (game.activeMap.isPlayerAt(x, y)) {
        batch.draw(this.playerTexture, x * tileSize, y * tileSize)
        font.draw(batch, "@", x * tileSize + mapXOffset, y * tileSize + mapYOffset)
      }

    }

  }

  private def matchSprite(tile: Tile) = tile match {
    case Tile.diggableTile => "YAY"
    case _ => tile

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
}