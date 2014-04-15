package org.diamonddwarf.stage

abstract class State()

case class Idle extends State()

/**
 * speed = how long it takes to traverse 1 tile
 */
case class Moving() extends State(){
  var speed = 0f
  var progress = 0f
}
case class Digging() extends State(){
  var speed = 0f
  var progress = 0f
}