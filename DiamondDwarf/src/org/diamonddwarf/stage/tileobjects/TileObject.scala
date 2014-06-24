package org.diamonddwarf.stage.tileobjects

import man.Man
import org.diamonddwarf.stage.Actor

abstract class TileObject(val layer: Int, collisionGroup: Int) extends Man(collisionGroup) with Actor

class Ground extends TileObject(0,1)
class GroundTopping extends TileObject(1,1)
class GroundObject extends TileObject(2,2)
class Gem(val value: Int, val name: String) extends TileObject(2,0) {
  override def toString = this.name + " of value " + this.value.toString
}
class Animate extends TileObject(3,3)

object TileObject {
  val hole = new GroundTopping()
  val stone = new GroundObject()
  val minedStone = new GroundObject()
  val base = new GroundTopping()
  val grass = new Ground()
}

object Gem {
  val noGem = new Gem(0, "No gem")
  val okayGem = new Gem(50, "Okay gem")
  val goodGem = new Gem(100, "Good gem")
  val fineGem = new Gem(150, "Fine gem")
  val epicGem = new Gem(200, "Epic gem")
  val bestGem = new Gem(250, "Best gem")
}

object NoObject extends TileObject(0,0)