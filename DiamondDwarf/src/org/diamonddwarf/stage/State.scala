package org.diamonddwarf.stage

abstract class State()

case class Idle extends State()

/**
 * speed = how long it takes to traverse 1 tile
 */
case class Moving(speed: Float) extends State(){
  var progress = 0f
}
case class Digging(speed: Float) extends State(){
  var progress = 0f
}