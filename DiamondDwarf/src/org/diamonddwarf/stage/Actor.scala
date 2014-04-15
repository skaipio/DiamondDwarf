package org.diamonddwarf.stage

import org.diamonddwarf.ui.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Actor() {
  var state: State = Idle()
  var nextState: State = Idle()
  var direction = Coordinate.Zero
  var facing = Coordinate.Right
  //var progress = speed
  var defaultTextureRegion: TextureRegion = null
  private var animationMap = scala.collection.mutable.Map[State, Animation]()

  def getTextureRegion = {
    var region: TextureRegion = null
    this.animationMap.get(state) match {
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
    this.state match{
      case s : Moving =>
        s.progress += delta
        if (s.progress >= s.speed)
          this.state = Idle()
        else s.progress += delta
      case s : Digging =>
        s.progress += delta
        if (s.progress >= s.speed)
          this.state = Idle()
        else s.progress += delta
      case _ =>
    }
  }

  def updateAnim(delta: Float) {
    this.animationMap.get(state) match {
      case Some(anim) => anim.update(delta)
      case _ =>
    }
  }
}