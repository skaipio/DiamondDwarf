package org.diamonddwarf.boards

import org.diamonddwarf.tileobjects._

final class BoardTemplate(val stageNum: Int, val gemCounts: Array[Int],
    val width: Int, val height: Int, val stones: Int,
    val baseX: Int, val baseY: Int, val time: Int, buildables: Array[String]){
  
  require(stones < width * height, "Not enough tiles for " + stones + " stones.")
  require(this.minTilesFromBorders(baseX, baseY, 1), "Not enough room for base at " + (baseX,baseY))
  
  val okayGems = gemCounts(0)
  val goodGems = gemCounts(1)
  val fineGems = gemCounts(2)
  val epicGems = gemCounts(3)
  val bestGems = gemCounts(4)
  val gemMap = this.createGemMap(okayGems, goodGems, fineGems, epicGems, bestGems)
  val workshops = this.matchWorkshops(buildables)
  
  override def toString = "StageTemplate for stage " + stageNum
  
  private def matchWorkshops(ids: Array[String]) = 
    for (id <- ids) yield Workshop.buildables.get(id).getOrElse(null)
  
  private def minTilesFromBorders(x: Int, y: Int, padding: Int) = {
    x >= padding && y >= padding && x < this.width - padding && y < this.height - padding
  }
  private def createGemMap(okayGems: Int, goodGems: Int, fineGems: Int, epicGems: Int, bestGems: Int) = {
    Map(OkayGem -> okayGems, GoodGem -> goodGems, FineGem -> fineGems, EpicGem -> epicGems, BestGem -> bestGems)
  }
}