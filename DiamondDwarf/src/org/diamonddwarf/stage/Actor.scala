package org.diamonddwarf.stage

import org.diamonddwarf.ui.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Actor(val speed: Float) {
  var state: State = Idle()
  var nextState: State = Idle()
  var direction = Coordinate.Zero
  var facing = Coordinate.Right
  var progress = speed
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

  def update(delta: Float) {
    this.updateAnim(delta)
    if (state == Moving()) {
      progress += delta
      if (progress >= speed) { state = Idle(); }
    } else { progress = speed; }
  }

  def updateAnim(delta: Float) {
    this.animationMap.get(state) match {
      case Some(anim) => anim.update(delta)
      case _ =>
    }
  }
}