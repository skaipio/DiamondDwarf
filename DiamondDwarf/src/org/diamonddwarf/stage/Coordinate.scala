package org.diamonddwarf.stage

class Coordinate(val x: Int, val y: Int) {

	def +(a: Coordinate) = new Coordinate(this.x + a.x, this.y + a.y) 

	override def toString = "(" + x + "," + y + ")"
} 
  
object Coordinate {
    val Zero = new Coordinate(0, 0)
	val Right = new Coordinate(1, 0)
    val Left = new Coordinate(-1, 0)
    val Up = new Coordinate(0, 1)
    val Down = new Coordinate(0, -1)
}