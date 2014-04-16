package org.diamonddwarf.resources

final class StageData(val stageNum: Int, val gemCounts: Array[Int],
    val width: Int, val height: Int, val stones: Int,
    val baseX: Int, val baseY: Int){
  override def toString = "StageData for stage " + stageNum
}