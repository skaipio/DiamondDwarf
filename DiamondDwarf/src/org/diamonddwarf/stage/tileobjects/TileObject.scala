package org.diamonddwarf.stage.tileobjects

import org.diamonddwarf.stage.Actor
import board.CollisionGroupable

abstract class TileObject(val layer: Int, collisionGrp: Int) extends Actor with CollisionGroupable {
  def collisionGroup = collisionGrp
}

class GroundObject extends TileObject(2, 2)
class Surface extends TileObject(1, 1)
class Ground extends TileObject(0, 0)
abstract case class Gem(val value: Int, val name: String) extends TileObject(Gem.layer, 2) {
  override def toString = this.name + " of value " + this.value.toString
}

object Hole extends Surface
object DwarfBase extends Surface
object Stone extends GroundObject
object MinedStone extends GroundObject
object Grass extends Ground

object OkayGem extends Gem(50, "Okay gem")
object GoodGem extends Gem(100, "Good gem")
object FineGem extends Gem(150, "Fine gem")
object EpicGem extends Gem(200, "Epic gem")
object BestGem extends Gem(250, "Best gem")

object Gem {
  val layer = 2
}