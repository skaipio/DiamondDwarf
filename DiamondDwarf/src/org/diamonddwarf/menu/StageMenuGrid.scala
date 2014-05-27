package org.diamonddwarf.menu

class StageMenuGrid(val rows: Int, val columns: Int, stageMenu: StageMenu) {

  private var stageSelectorX = 0
  private var stageSelectorY = 0

  // def selectStage(stageNum: Int) { this.stageSelector = stageNum }

  private def cycler(n: Int, ceil: Int) =
    ((n % ceil) + ceil) % ceil

  def selectedStage = stageSelectorX + (columns * stageSelectorY)

  def selectorUp {
    stageSelectorY = cycler(stageSelectorY - 1, rows)
    if (selectedStage >= stageMenu.stageCount) stageSelectorY -= 1
  }

  def selectorDown {
    stageSelectorY = cycler(stageSelectorY + 1, rows)
    if (selectedStage >= stageMenu.stageCount) stageSelectorY = 0
  }

  def selectorLeft {
    stageSelectorX -= 1
    if (stageSelectorX < 0) {
      selectorUp
      stageSelectorX = columns - 1
      if (selectedStage > stageMenu.stageCount) {
        stageSelectorX = rows * columns - stageMenu.stageCount
      }
    }

  }

  def selectorRight {
    stageSelectorX += 1
    if (stageSelectorX >= columns || selectedStage >= stageMenu.stageCount) {
      stageSelectorX = 0
      selectorDown
    }
  }

}