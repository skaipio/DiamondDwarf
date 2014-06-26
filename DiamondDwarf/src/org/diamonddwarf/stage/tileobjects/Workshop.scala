package org.diamonddwarf.stage.tileobjects

abstract class Workshop extends GroundObject{
  def use(p: Player)
}

class Refinery extends Workshop {
  override def use(p: Player) {
    p.give(Gem.bestGem)
  }
}

class Replenisher extends Workshop {
  override def use(p: Player) {
    p.setShovelUsages(30)
  }
}

object Workshop {
	val layer = 2
	val refinery = new Refinery
	val replenisher = new Replenisher	
	val buildables = Map("refinery" -> refinery, "replenisher" -> replenisher)
}