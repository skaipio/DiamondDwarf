package org.diamonddwarf.stage

import scala.Array.canBuildFrom
import scala.collection.mutable.Set
import scala.collection.mutable.IndexedSeq
import scala.collection.Iterable
import scala.util.Random

object Stage {

  val stage1 = new Stage(10, 10, 10, Map(Gem.goodGem -> 4, Gem.fineGem -> 3))

}

class Stage(width: Int, height: Int, stones: Int, gemList: Map[Gem, Int]) extends TileMap(width, height) {
  val exitToHomeBase : Coordinate = new Coordinate(width/2, 0)
  this.playerPosition = exitToHomeBase
  
  {
    var coordinatePairs = (0 until height).flatMap(x => (0 until width).map(y => (x, y)))
    coordinatePairs = Random.shuffle(coordinatePairs)

    for (gems <- gemList) {
      for (_ <- 0 until gems._2) {
        val (x, y) = coordinatePairs.head
        this.setTileAt(x, y, Tile.newTileWithGem(gems._1))
        coordinatePairs = coordinatePairs.tail
      }
    }

    for (_ <- 0 until stones) {
      val (x, y) = coordinatePairs.head
      this.setTileAt(x, y, Tile.newTileWithStone)
      coordinatePairs = coordinatePairs.tail
    }
    
    this.setTileAt(exitToHomeBase, Tile.newEntryToBase)    
  }
}


