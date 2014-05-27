package org.diamonddwarf.menu

import org.diamonddwarf.factories.StageFactory

class StageMenu(stageFactory: StageFactory) {

  private var stageSelector = 0

  def selectStage(stageNum: Int) { this.stageSelector = stageNum }
  
  def selectedStage = this.stageSelector
  
  def stageCount = stageFactory.stageTemplates.size

  def createStage = stageFactory.createStage(stageSelector)

  def getStageInfo(stageNum: Int) = stageFactory.getStageTemplate(stageNum)

}