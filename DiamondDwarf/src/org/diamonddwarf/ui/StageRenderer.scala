package org.diamonddwarf.ui

import org.diamonddwarf.stage.Stage
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.diamonddwarf.DiamondDwarf

class StageRenderer(game: DiamondDwarf) {
  private var shapeR = new ShapeRenderer
  private var batch = new SpriteBatch
  private var font = new BitmapFont
  
  private var mapXOffset = 20
  private var mapYOffset = 50
  
  private var inventoryXOffset = 250
  private var inventoryYOffset = 220
  
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
        font.draw(batch, "@", x*20+mapXOffset, y*20+mapYOffset)
      } else{
        font.draw(batch, game.activeMap.getTileAt(x, y).toString, x*20+mapXOffset, y*20+mapYOffset)
      }
    }

  }

  private def renderPlayerInventory {
    var i = 1
    font.draw(batch, game.player.name + "'s inventory", inventoryXOffset, inventoryYOffset)
    for ((gem, count) <- game.player.inventory) {
      font.draw(batch, gem + ": " + count, inventoryXOffset, inventoryYOffset - i * 20)
      i += 1
    }
  }
}