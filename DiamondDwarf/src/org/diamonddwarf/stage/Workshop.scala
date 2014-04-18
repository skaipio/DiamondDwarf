package org.diamonddwarf.stage

import org.diamonddwarf.items.Gem
import org.diamonddwarf.factories.AnimationFactory

abstract class Workshop extends TileObject(false) with Actor {
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
	val refinery = new Refinery
	val replenisher = new Replenisher
}