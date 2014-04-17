package org.diamonddwarf.stage

import org.diamonddwarf.ui.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.audio.Sound
import scala.collection.mutable.MutableList
import org.diamonddwarf.ui.Effect

class Actor(val states : States) {
  var activeState: State = null
  var nextState: State = null
  
  var position = Coordinate(0,0)
  
  var direction = Coordinate.Zero
  var facing = Coordinate.Right

  var defaultTextureRegion: TextureRegion = null
  private var animationMap = scala.collection.mutable.Map[State, Animation]()
  private val soundMap = scala.collection.mutable.Map[State, Sound]()
  private val methodMap = scala.collection.mutable.Map[State, State => Unit]()
  private var effects = MutableList[Effect]()

  def getTextureRegion = {
    var region: TextureRegion = null
    this.animationMap.get(this.activeState) match {
      case Some(anim) =>
        region = anim.getCurrentFrame
      case _ => region = defaultTextureRegion
    }
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
    if (this.activeState.toBePerformed != null) {
      this.activeState.toBePerformed()
    }
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
  
  def associateStateWithMethod(state: State, method: State => Unit){
    this.methodMap += state -> method
  }
  
  def getEffects = this.effects
  
  def addEffect(effect: Effect){
    effect +=: this.effects
  }
  
  def resetAnimOfState(state: State){
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
  
  private def updateEffects(delta: Float){
    this.effects = this.effects.filter(!_.isFinished)
    for(effect <- this.effects) effect.update(delta)
  }
  
  private def updateState(delta: Float) {
    this.activeState.progress += delta
    if (this.activeState.progress >= this.activeState.speed) {
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