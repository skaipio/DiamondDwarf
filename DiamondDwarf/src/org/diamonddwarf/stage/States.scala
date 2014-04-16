package org.diamonddwarf.stage


class States(moveSpeed: Float, digSpeed: Float) {

  val idle = new State(0)
  val moving = new State(moveSpeed)
  val digging = new State(digSpeed)
  val detectingGems = new State(0.2f)

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