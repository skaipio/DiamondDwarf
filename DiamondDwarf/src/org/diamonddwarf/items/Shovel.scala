package org.diamonddwarf.items

case class Shovel private(val name: String, var digsLeft: Int) { 
  override def toString = name + " with " + digsLeft + {if (digsLeft == 1) " dig" else " digs"} + " left"
}

object Shovel{
  val shabbyShovel = new Shovel("Shabby shovel", 30)
}