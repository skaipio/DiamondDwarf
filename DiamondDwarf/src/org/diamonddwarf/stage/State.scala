package org.diamonddwarf.stage

abstract class State(var speed: Float) {

  var progress = 0.0f
  var toBePerformed: () => Unit = null

  def onceComplete(perform: => Unit) {
    toBePerformed = () => perform
  }

  def reset {
    this.progress = 0
    this.toBePerformed = null
  }

}

case class Idle extends State(0)
case class Moving(_speed: Float) extends State(_speed)
case class Digging(_speed: Float) extends State(_speed)