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
import org.diamonddwarf.resources.ResourceLoader

class InventoryRenderer(batch: SpriteBatch, resourceLoader: ResourceLoader) {
  private val font = new BitmapFont
  private val tileSize = 64
  
  private val stageTimer = new StageTimer(batch, resourceLoader)
  private val playerBar = new PlayerBar(batch, resourceLoader)

  font.setColor(Color.BLACK)

  def create {
    font.setColor(Color.WHITE)
  }

  def render() {
    batch.begin()
    this.playerBar.render
    this.stageTimer.renderTimer
    this.renderPlayerInventory
    batch.end()
  }

  def dispose = {
  }

  private def renderPlayerInventory {
    val offsetX = DiamondDwarf.activeMap.width * tileSize + 20
    val offsetY = DiamondDwarf.activeMap.height * tileSize - 20
    var i = 1
    var additionalXOffset = 0
    var additionalYOffset = -60
    font.draw(batch, "time: " + DiamondDwarf.activeMap.currentTime.toInt, offsetX, offsetY - 40)
    font.draw(batch, DiamondDwarf.player.name + "'s inventory", offsetX, offsetY)
    font.draw(batch, "score: " + DiamondDwarf.player.score, offsetX, offsetY - 20)
    if (DiamondDwarf.player.shovel != null) {
      font.draw(batch, DiamondDwarf.player.shovel.toString, offsetX, offsetY + additionalYOffset)
      additionalYOffset -= 20
    }
    for ((gem, count) <- DiamondDwarf.player.inventory) {
      font.draw(batch, gem + ": " + count, offsetX, offsetY + additionalYOffset - i * 20)
      i += 1
    }
  }
}