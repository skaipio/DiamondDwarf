package org.diamonddwarf.resources

final class StageData(val stageNum: Int, val gemCounts: Array[Int],
    val width: Int, val height: Int, val stones: Int,
    val baseX: Int, val baseY: Int, val time: Int, val buildables: Array[String]){
  override def toString = "StageTemplate for stage " + stageNum
}