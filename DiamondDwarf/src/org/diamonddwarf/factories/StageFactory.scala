package org.diamonddwarf.factories

import org.diamonddwarf.resources.ResourceLoader
import org.diamonddwarf.items.Gem
import org.diamonddwarf.stage.Stage
import org.diamonddwarf.stage.Coordinate
import org.diamonddwarf.stage.tileobjects.Workshop
import org.diamonddwarf.resources.StageTemplate

class StageFactory(val stageTemplates: Array[StageTemplate], tileFactory: TileFactory) {

  def getStageTemplate(stageNum: Int) = {
    require(stageNum >= 0 && stageNum <= stageTemplates.length, "Stage number " + stageNum + " does not exist.")
    stageTemplates(stageNum)
  }

  def createStage(stageNumber: Int) = {
    val stageData = getStageTemplate(stageNumber)
    val okayGems = stageData.gemCounts(0)
    val goodGems = stageData.gemCounts(1)
    val fineGems = stageData.gemCounts(2)
    val epicGems = stageData.gemCounts(3)
    val bestGems = stageData.gemCounts(4)
    val gemMap = this.createGemMap(okayGems, goodGems, fineGems, epicGems, bestGems)
    new Stage(
      stageData.width,
      stageData.height,
      tileFactory,
      stageData.stones,
      gemMap,
      Coordinate(stageData.baseX, stageData.baseY),
      stageData.time,
      matchWorkshops(stageData.buildables))
  }

  private def matchWorkshops(ids: Array[String]) =
    for (id <- ids) yield {
      Workshop.buildables.get(id) match {
        case Some(workshop) => workshop
        case _ => null
      }
    }

  private def createGemMap(okayGems: Int, goodGems: Int, fineGems: Int, epicGems: Int, bestGems: Int) = {
    Map(Gem.okayGem -> okayGems, Gem.goodGem -> goodGems, Gem.fineGem -> fineGems, Gem.epicGem -> epicGems, Gem.bestGem -> bestGems)
  }
}