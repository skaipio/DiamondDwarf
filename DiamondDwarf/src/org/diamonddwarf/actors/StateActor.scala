package org.diamonddwarf.actors

trait StateActor extends DDActor {
  protected[this] var state : State = Idle()
  protected[this] var timer : Float = 0f
  
  abstract override def update(delta : Float){
    super.update(delta)
    if (state.timed && timer >= state.delay){
      this.setState(Idle())
      timer = 0f
    }
    else{
      timer += delta
    }
  }
  
  def setState(state : State) {
    this.state = state
    this.timer = 0f
  }
  
  def getState() = this.state
}

abstract class State {
  var delay = 0f
  val timed : Boolean = false
}

case class Idle() extends State
case class Moving() extends State{
  override val timed = true
}