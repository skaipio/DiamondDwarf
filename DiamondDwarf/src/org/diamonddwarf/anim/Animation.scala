package org.diamonddwarf.anim

import org.diamonddwarf.Resources
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion

abstract class Animation(resources: Resources) {
  protected var animId: Int = _
  protected var totalFrameTime_ : Float = _
  protected var timer: Float = _
  val frames = resources.animations.getOrElse(animId, List[AtlasRegion]())
  val frameTimes: Array[Float] = new Array(frames.size)
  def assignFrameTimes(times: Float*) {
    for (i <- (0 until frameTimes.size)) frameTimes(i) = times(i)
  }
  def totalFrameTime = this.totalFrameTime_
  def advance(delta: Float) {
    this.timer = (this.timer+delta) % this.totalFrameTime
  }
  def getCurrentFrame = {
    if (this.frames.size == 0) None
    else {
      var time = 0f
      var i = 0
      var frame = this.frames(i)
      while (time <= timer) {
        time += this.frameTimes(i)
        i += 1
      }
      Some(this.frames(i-1))
    }

  }
}