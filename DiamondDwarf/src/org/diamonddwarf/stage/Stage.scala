package org.diamonddwarf.stage

import org.diamonddwarf.Coordinate
import scala.Array.canBuildFrom
import scala.collection.mutable.Set
import scala.collection.mutable.IndexedSeq
import scala.collection.Iterable
import scala.util.Random

object Stage {
  
  val stage1 = new Stage(10, 10, 10, Map(Gem.goodGem -> 4, Gem.fineGem -> 3))
  
}

class Stage(val width: Int, val height: Int, stones: Int, gemList: Map[Gem, Int]) extends Traversable[Tile] {
  private val stageMap = Array.fill(height, width)(new Tile(".",Gem.noGem))
  
  {
    var coordinatePairs = (0 until height).flatMap(x => (0 until width).map(y => (x,y)))
    coordinatePairs = Random.shuffle(coordinatePairs)
    
    for (gems <- gemList){
      for(_ <- 0 until gems._2){
        val (x,y) = coordinatePairs.head
        this.stageMap(x)(y) = new Tile(".", gems._1)
        coordinatePairs = coordinatePairs.tail
      }
    }
    
     for(_ <- 0 until stones){
        val (x,y) = coordinatePairs.head
        this.stageMap(x)(y) = new Tile("#", false)
        coordinatePairs = coordinatePairs.tail
      }
  }
  
  var playerPosition = Coordinate.Zero
  
  stageMap(5)(5) = new Tile("#", false)


  def tileAt(x: Int, y: Int) = stageMap(y)(x)
  def tileAt(coordinate: Coordinate) = stageMap(coordinate.y)(coordinate.x) 
  
  def inBounds(coordinate: Coordinate) : Boolean = this.inBounds(coordinate.x, coordinate.y)
  
  def inBounds(x: Int, y: Int) = x >= 0 && y >= 0 && x < this.width && y < this.height
  
  def setPlayerPosition(coordinate: Coordinate) { this.playerPosition = coordinate }
  def setPlayerPosition(x: Int, y: Int) { this.playerPosition = new Coordinate(x, y) }
  
  def playerTile = stageMap(playerPosition.y)(playerPosition.x)
  def isPlayerAt(t: Tile) = t == playerTile
  def isPlayerAt(x: Int, y: Int): Boolean = this.isPlayerAt(this.tileAt(x, y))
  
  override def foreach[U](f: Tile => U) = {
    for(row <- stageMap; tile <- row) f(tile)
  }
 
  // Whole map as symbols
   override def toString = {
    var returning = new StringBuilder()
    for(row <- this.stageMap.reverse){
      for(t <- row){
        if (t == playerTile) returning += '@'
        else returning ++= t.toString
      }
      returning += '\n'
    }
    returning.toString
    
  }
}


