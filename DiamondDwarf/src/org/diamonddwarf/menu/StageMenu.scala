package org.diamonddwarf.menu

import org.diamonddwarf.factories.StageFactory

class StageMenu(stageFactory: StageFactory) {
  
  def stageCount = stageFactory.stageTemplates.size

  def createStage(n: Int) = stageFactory.createStage(n)

  def getStageInfo(stageNum: Int) = stageFactory.getStageTemplate(stageNum)

}