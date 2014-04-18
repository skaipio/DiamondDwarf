package org.diamonddwarf.stage

class State(var speed: Float) {

  var progress = 0.0f
  var toBePerformed: () => Unit = null

  def onceComplete(perform: => Unit) {
    toBePerformed = () => perform
  }

  def reset {
    this.progress = 0
  }
  
  def completed = this.progress >= this.speed
  
  def update(delta: Float) {
    if (progress == 0) 
      doFirst()
    this.progress += delta
    if (this.progress >= this.speed) {
      doLast()

    }
  }

  var doFirst : () => Unit = () => {}
  var doLast : () => Unit  = () => {}
  
}

class GemsFound extends State(0f){
  var gemsFound = 0
}

