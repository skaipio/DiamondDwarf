package org.diamonddwarf.items

case class Equipment private(val name: String, var usesLeft: Int) { 
  override def toString = name + " with " + usesLeft + {if (usesLeft == 1) " dig" else " digs"} + " left"
}

object Equipment{
  val shabbyShovel = new Equipment("Shabby shovel", 30)
  val sloppyPickaxe = new Equipment("Sloppy shovelaxe", 30)
}