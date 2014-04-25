package org.diamonddwarf.menu

import org.diamonddwarf.factories.StageFactory

class StageMenu(stageFactory: StageFactory) {

  def createStage(stageNum: Int) = stageFactory.createStage(stageNum)

  def getStageInfo(stageNum: Int) = stageFactory.getStageTemplate(stageNum)

}