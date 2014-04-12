package org.diamonddwarf.ui

import org.diamonddwarf.stage.Stage
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.diamonddwarf.DiamondDwarf

class StageRenderer(game: DiamondDwarf) {
  private val shapeR = new ShapeRenderer
  private val batch = new SpriteBatch
  private val font = new BitmapFont

  private val mapXOffset = 20
  private val mapYOffset = 50

  private val inventoryXOffset = 250
  private val inventoryYOffset = 220

  font.setColor(Color.BLACK)

  def render() {
    shapeR.begin(ShapeRenderer.ShapeType.Filled)

    shapeR.end()
    batch.begin()
    this.renderTiles
    this.renderPlayerInventory
    batch.end()
  }

  def dispose = batch.dispose(); shapeR.dispose()

  private def renderTiles {
    //    for(y <- 0 until game.activeMap.height; x <- 0 until game.activeMap.width){
    //      if (game.activeMap.isPlayerAt(x, y)){
    //        shapeR.setColor(Color.RED)
    //      }else if (game.activeMap.getTileAt(x, y).isDug){
    //        shapeR.setColor(Color.ORANGE)
    //      }else if (!game.activeMap.getTileAt(x, y).passable){
    //        shapeR.setColor(Color.GRAY)
    //      }else{
    //        shapeR.setColor(Color.BLACK)
    //      }
    //      shapeR.rect(x*20, y*20, 20, 20)
    //    }   
    for (y <- 0 until game.activeMap.height; x <- 0 until game.activeMap.width) {
      if (game.activeMap.isPlayerAt(x, y)) {
        font.draw(batch, "@", x * 20 + mapXOffset, y * 20 + mapYOffset)
      } else {
        font.draw(batch, game.activeMap.getTileAt(x, y).toString, x * 20 + mapXOffset, y * 20 + mapYOffset)
      }
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
}