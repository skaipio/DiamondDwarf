package org.diamonddwarf.stage

import org.diamonddwarf.ui.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Actor(val speed: Float) {
  var state: State = Idle()
  var direction = Coordinate.Zero
  var progress = speed
  var moving = false
  var defaultTextureRegion: TextureRegion = null  
  private var animationMap = scala.collection.mutable.Map[State, Animation]()
  
  def getTextureRegion = {
    var region : TextureRegion = null
    this.animationMap.get(state) match {
      case Some(anim) => region = anim.getCurrentFrame
      case _ => region = defaultTextureRegion
    }
    region
  }
  
  def associateStateWithAnim(state: State, anim: Animation) {
    this.animationMap += state -> anim
  }
  
  def update(delta: Float) {
    this.updateAnim(delta)
    if (moving) {
      progress += delta
      if (progress >= speed) {moving = false; state = Idle() }
    }
    else { progress = speed; }
  }
  
  def updateAnim(delta: Float){
    this.animationMap.get(state) match{
      case Some(anim) => anim.update(delta)
      case _ =>
    }
  }
}