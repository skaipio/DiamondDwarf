package org.diamonddwarf.stage

import org.diamonddwarf.ui.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Actor() {
  
  var states : States = null
  
  var direction = Coordinate.Zero
  var facing = Coordinate.Right

  var defaultTextureRegion: TextureRegion = null
  private var animationMap = scala.collection.mutable.Map[State, Animation]()

  def getTextureRegion = {
    var region: TextureRegion = null
    this.animationMap.get(states.activeState) match {
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

  def associateStateWithAnim(state: State, anim: Animation) {
    this.animationMap += state -> anim
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
    this.states.update(delta)
  }

  def updateAnim(delta: Float) {
    this.animationMap.get(this.states.activeState) match {
      case Some(anim) => anim.update(delta)
      case _ =>
    }
  }
}