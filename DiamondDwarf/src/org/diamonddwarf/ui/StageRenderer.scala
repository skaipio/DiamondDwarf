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
  
  font.setColor(Color.BLACK)
  
  def render {
    shapeR.begin(ShapeRenderer.ShapeType.Filled)   
    this.renderTiles  
    shapeR.end()
    batch.begin()
    this.renderPlayerInventory
    batch.end()
  }
  
  def dispose = batch.dispose(); shapeR.dispose()
  
  private def renderTiles {
    for(y <- 0 until game.activeStage.height; x <- 0 until game.activeStage.width){
      if (game.activeStage.isPlayerAt(x, y)){
        shapeR.setColor(Color.RED)
      }else if (game.activeStage.tileAt(x, y).isDug){
        shapeR.setColor(Color.ORANGE)
      }else if (!game.activeStage.tileAt(x, y).passable){
        shapeR.setColor(Color.GRAY)
      }else{
        shapeR.setColor(Color.BLACK)
      }
      shapeR.rect(x*20, y*20, 20, 20)
    }   
  }
  
  private def renderPlayerInventory{
    var i = 1
    font.draw(batch, game.player.name + "'s inventory", 250, 200)
    for((gem, count) <- game.player.inventory){
      font.draw(batch, gem + ": " + count, 250, 200-i*20)
      i+=1
    }   
  }
}