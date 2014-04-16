package org.diamonddwarf.stage

class State(var speed: Float) {

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

