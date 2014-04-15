package org.diamonddwarf.stage

class States(moveSpeed: Float, digSpeed: Float) {

  val idle = Idle()
  val moving = Moving(moveSpeed)
  val digging = Digging(digSpeed)

  var activeState: State = idle
  var nextState: State = idle

  def activate(state: State) {   
    if (this.activeState.toBePerformed != null) {
      this.activeState.toBePerformed()
    }
    this.activeState = state
    state.reset
  }

  def update(delta: Float) {
    this.activeState.progress += delta
    if (this.activeState.progress >= this.activeState.speed) {
      this.activate(nextState)
    }
  }

}