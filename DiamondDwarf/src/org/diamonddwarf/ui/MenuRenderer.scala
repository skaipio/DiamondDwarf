package org.diamonddwarf.ui

import org.diamonddwarf.menu.StageMenu
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.diamonddwarf.menu.StageMenuGrid

class MenuRenderer(batch: SpriteBatch, resourceLoader: ResourceLoader, stageMenu: StageMenu, stageMenuGrid: StageMenuGrid) {

  private val padding = 32

  private val rows = stageMenuGrid.rows
  private val columns = stageMenuGrid.columns

  private val offsetX = 0
  private val offsetY = 300

  private val infoTextOffsetX = 10
  private val infoTextOffsetY = 50

  private val infoTextLineHeight = 20

  private val font = new BitmapFont
  font.setColor(Color.BLACK)

  private def renderGrid {
    var stageNumber = 0
    for (row <- rows to 0 by -1; column <- 0 until columns) {
      if (stageNumber >= stageMenu.stageCount) return
      if (stageMenuGrid.selectedStage == stageNumber) { batch.setColor(Color.RED) }
      val drawX = offsetX + column * (padding + 64)
      val drawY = offsetY + row * (padding + 64)
      batch.draw(resourceLoader.stageMenuCell, drawX, drawY)
      batch.setColor(Color.WHITE)
      renderStageInfo(stageNumber, drawX, drawY)
      stageNumber += 1
    }
  }

  private def renderStageInfo(stage: Int, x: Int, y: Int) {
    font.draw(batch, stage + "", x + infoTextOffsetX, y + infoTextOffsetY)
    if (stageMenu.stageCount <= stage) { return }
   // font.draw(batch, "" + stageMenu.getStageInfo(stage).gemCounts(0), x + infoTextOffsetX, y + infoTextOffsetY - infoTextLineHeight)
  }

  def render {
    batch.begin()
    renderGrid
    batch.end()
  }

}