package org.diamonddwarf.ui

import org.diamonddwarf.stage._
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.diamonddwarf.DiamondDwarf
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture.TextureFilter
import scala.util.Random
import com.badlogic.gdx.graphics.OrthographicCamera

class InventoryRenderer(game: DiamondDwarf, private val batch : SpriteBatch) {
  private val font = new BitmapFont
  private val tileSize = 64

  font.setColor(Color.BLACK)

  def create {
    font.setColor(Color.WHITE)
  }

  def render() {
    batch.begin()
    this.renderPlayerInventory
    batch.end()
  }

  def dispose = {
  }

  private def renderPlayerInventory {
    val offsetX = game.activeMap.width * tileSize + 20
    val offsetY = game.activeMap.height * tileSize - 20
    var i = 1
    var additionalXOffset = 0
    var additionalYOffset = -20
    font.draw(batch, game.player.name + "'s inventory", offsetX, offsetY)
    if (game.player.shovel != null) {
      font.draw(batch, game.player.shovel.toString, offsetX, offsetY + additionalYOffset)
      additionalYOffset -= 20
    }
    for ((gem, count) <- game.player.inventory) {
      font.draw(batch, gem + ": " + count, offsetX, offsetY + additionalYOffset - i * 20)
      i += 1
    }
  }
}