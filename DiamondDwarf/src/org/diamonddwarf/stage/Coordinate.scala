package org.diamonddwarf.stage

case class Coordinate(val x: Int, val y: Int) extends Equals {

	def +(a: Coordinate) = new Coordinate(this.x + a.x, this.y + a.y) 
	
	
	override def toString = "(" + x + ", " + y + ")"
  
  def canEqual(other: Any) = {
	  other.isInstanceOf[org.diamonddwarf.stage.Coordinate]
	}
  
  override def equals(other: Any) = {
	  other match {
	    case that: org.diamonddwarf.stage.Coordinate => that.canEqual(Coordinate.this) && x == that.x && y == that.y
	    case _ => false
	  }
	}
  
  override def hashCode() = {
	  val prime = 41
	  prime * (prime + x) + y 
	}
} 
  
object Coordinate {
    val Zero = new Coordinate(0, 0)
	val Right = new Coordinate(1, 0)
    val Left = new Coordinate(-1, 0)
    val Up = new Coordinate(0, 1)
    val Down = new Coordinate(0, -1)
}