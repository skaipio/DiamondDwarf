package org.diamonddwarf.factories

import org.diamonddwarf.resources.ResourceLoader
import org.diamonddwarf.items.Gem
import org.diamonddwarf.stage.Stage
import org.diamonddwarf.stage.Coordinate

class StageFactory(private val resources: ResourceLoader) {
  
  def createStage(stageNumber: Int) = {
    val stageData = resources.getStageData(stageNumber)
    val okayGems = stageData.gemCounts(0)
    val goodGems = stageData.gemCounts(1)
    val fineGems = stageData.gemCounts(2)
    val epicGems = stageData.gemCounts(3)
    val bestGems = stageData.gemCounts(4)
    val gemMap = this.createGemMap(okayGems, goodGems, fineGems, epicGems, bestGems)
    new Stage(stageData.width, stageData.height, stageData.stones, gemMap, Coordinate(stageData.baseX, stageData.baseY))
  }

  private def createGemMap(okayGems: Int, goodGems: Int, fineGems: Int, epicGems: Int, bestGems: Int) = {
    Map(Gem.okayGem -> okayGems, Gem.goodGem -> goodGems, Gem.fineGem -> fineGems, Gem.epicGem -> epicGems, Gem.bestGem -> bestGems)
  }
}