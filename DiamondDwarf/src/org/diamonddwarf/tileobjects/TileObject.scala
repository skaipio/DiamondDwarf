package org.diamonddwarf.tileobjects

import fs.tileboard.board.CollisionGroupable

abstract class TileObject(val layer: Int, val collisionGroup: Int) extends CollisionGroupable{
  
  val diggable = true
  
  override def toString = this.getClass().getName().split('.').last.dropRight(1)
  
}

class GroundObject extends TileObject(2, CollisionGroups.groundObjects)
class Surface extends TileObject(1, CollisionGroups.surface){
  override val diggable = false
}
class Ground extends TileObject(0, CollisionGroups.ground)
abstract case class Gem(val value: Int, val name: String) extends TileObject(Gem.layer, CollisionGroups.ground) {
  override def toString = this.name + " of value " + this.value.toString
}

object Hole extends Surface
object DwarfBase extends Surface
object Stone extends GroundObject
object MinedStone extends Surface
object Grass extends Ground

object OkayGem extends Gem(50, "Okay gem")
object GoodGem extends Gem(100, "Good gem")
object FineGem extends Gem(150, "Fine gem")
object EpicGem extends Gem(200, "Epic gem")
object BestGem extends Gem(250, "Best gem")

object CollisionGroups {
  val groundObjects = 2
  val surface = 1
  val ground = 0
  val collisionSet : Set[(Int, Set[Int])] = Set()
}

object Gem {
  val layer = 2
}