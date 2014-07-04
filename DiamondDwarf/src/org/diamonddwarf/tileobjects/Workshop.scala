package org.diamonddwarf.tileobjects

abstract class Workshop extends GroundObject{
  def use()
}

object Refinery extends Workshop {
  override def use() {
    Player.give(BestGem)
  }
}

object Replenisher extends Workshop {
  override def use() {
    Player.setShovelUsages(30)
  }
}

object Workshop {
	val layer = 2	
	val buildables = Map("refinery" -> Refinery, "replenisher" -> Replenisher)
}