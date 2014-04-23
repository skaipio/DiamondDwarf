package org.diamonddwarf.stage

import org.diamonddwarf.ui.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.audio.Sound
import scala.collection.mutable.MutableList
import org.diamonddwarf.ui.Effect
import org.diamonddwarf.ui.Drawable

trait Actor extends Drawable {
  val states = new States
  var activeState: State = states.idle
  var nextState: State = states.idle

  var position = Coordinate(0, 0)
  def front = position + facing

  var direction = Coordinate.Zero
  var facing = Coordinate.Right

  val animationMap = scala.collection.mutable.Map[State, Animation]()
  private val soundMap = scala.collection.mutable.Map[State, Sound]()
  private val methodMap = scala.collection.mutable.Map[State, State => Unit]()
  private var effects = MutableList[Effect]()

  override def getTexture = {
    var region: TextureRegion = null
    this.animationMap.get(this.activeState) match {
      case Some(anim) =>
        region = anim.getCurrentFrame
      case _ =>
        this.animationMap.get(this.states.idle) match {
          case Some(anim) => region = anim.getCurrentFrame
          case _ => region = super.getTexture
        }
    }
    if (region != null)
      flipToDirection(region)
    region
  }

  private def flipToDirection(region: TextureRegion) {
    if (this.facing == Coordinate.Right && !region.isFlipX()) {
      region.flip(true, false)
    } else if (this.facing == Coordinate.Left && region.isFlipX()) {
      region.flip(true, false)
    }

  }

  def activate(state: State) {
    this.activeState = state
    state.reset
    this.soundMap.get(state) match {
      case Some(sound) => sound.play()
      case _ =>
    }
    this.methodMap.get(state) match {
      case Some(method) => method(state)
      case _ =>
    }
  }

  def associateStateWithAnim(state: State, anim: Animation) {
    this.animationMap += state -> anim
  }

  def associateStateWithSound(state: State, sound: Sound) {
    this.soundMap += state -> sound
  }

  def associateStateWithMethod(state: State, method: State => Unit) {
    this.methodMap += state -> method
  }

  def getEffects = this.effects

  def addEffect(effect: Effect) {
    effect +=: this.effects
  }

  def resetAnimOfState(state: State) {
    this.animationMap.get(state) match {
      case Some(anim) =>
        anim.restart
      case _ =>
    }
  }

  def update(delta: Float) {
    this.updateAnim(delta)
    this.updateEffects(delta)
    this.updateState(delta)
  }

  private def updateEffects(delta: Float) {
    this.effects = this.effects.filter(!_.isFinished)
    for (effect <- this.effects) effect.update(delta)
  }

  private def updateState(delta: Float) {
    this.activeState.update(delta)
    if (this.activeState.completed) {
      this.activate(nextState)
      this.nextState = this.states.idle
    }
  }

  private def updateAnim(delta: Float) {
    this.animationMap.get(this.activeState) match {
      case Some(anim) => anim.update(delta)
      case _ =>
    }
  }
}