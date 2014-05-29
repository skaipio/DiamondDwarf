package org.diamonddwarf.ui

import org.diamonddwarf.menu.StageMenu
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import org.diamonddwarf.menu.StageMenuGrid
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.diamonddwarf.stage.Tile

class MenuRenderer(batch: SpriteBatch, resourceLoader: ResourceLoader, stageMenu: StageMenu, stageMenuGrid: StageMenuGrid) {

  private val padding = 32

  private val rows = stageMenuGrid.rows
  private val columns = stageMenuGrid.columns

  private val offsetX = 10
  private val offsetY = 300

  private val backgroundOverlayColor = Color.LIGHT_GRAY

  private val levelTextOffsetX = 10
  private val levelTextOffsetY = 50

  private val infoTextLineHeight = 20

  private val infoboxX = 10
  private val infoboxY = 50
  private val infoboxWidth = 500
  private val infoboxHeight = 300

  private val infoboxTextOffsetX = 10
  private val infoboxTextOffsetY = 10

  private val shapeRenderer = new ShapeRenderer()

  private val font = new BitmapFont
  font.setColor(Color.BLACK)

  private def renderBackground {
    batch.setColor(backgroundOverlayColor)
    for (row <- 0 to 20; column <- 0 to 20) {
      batch.draw(resourceLoader.diggableTileTexture.get(0), row * 64, column * 64)

    }
    batch.setColor(Color.WHITE)

  }

  private def renderGrid {
    var stageNumber = 0
    for (row <- rows to 0 by -1; column <- 0 until columns) {
      if (stageNumber >= stageMenu.stageCount) return
      if (stageMenuGrid.selectedStage == stageNumber) { batch.setColor(Color.RED) }
      val drawX = offsetX + column * (padding + 64)
      val drawY = offsetY + row * (padding + 64)
      batch.draw(resourceLoader.stageMenuCell, drawX, drawY)
      batch.setColor(Color.WHITE)
      renderStageNumber(stageNumber, drawX, drawY)
      stageNumber += 1
    }
  }

  private def renderStageNumber(stage: Int, x: Int, y: Int) {
    font.draw(batch, stage + "", x + levelTextOffsetX, y + levelTextOffsetY)
    if (stageMenu.stageCount <= stage) { return }
    // font.draw(batch, "" + stageMenu.getStageInfo(stage).gemCounts(0), x + infoTextOffsetX, y + infoTextOffsetY - infoTextLineHeight)
  }

  private def renderStageInfoBox() {
    this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
    this.shapeRenderer.rect(infoboxX, infoboxY, infoboxWidth, infoboxHeight)
    this.shapeRenderer.end()
  }

  private def renderStageInfo() {
    font.draw(batch, stageMenu.getStageInfo(stageMenuGrid.selectedStage).gemCounts(0) + "", infoboxX + infoboxTextOffsetX, infoboxY + infoboxHeight - infoboxTextOffsetY)
    font.draw(batch, stageMenu.getStageInfo(stageMenuGrid.selectedStage).gemCounts(1) + "", infoboxX + infoboxTextOffsetX, infoboxY + infoboxHeight - infoboxTextOffsetY - infoTextLineHeight)
    font.draw(batch, stageMenu.getStageInfo(stageMenuGrid.selectedStage).gemCounts(2) + "", infoboxX + infoboxTextOffsetX, infoboxY + infoboxHeight - infoboxTextOffsetY - infoTextLineHeight * 2)

  }

  def render {
    batch.begin()
    // renderBackground
    renderGrid
    renderStageInfoBox
    renderStageInfo
    batch.end()
  }

}