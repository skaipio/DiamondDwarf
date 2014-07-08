package org.diamonddwarf.anim

import org.diamonddwarf.Resources
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import org.diamonddwarf.actors.State
import org.diamonddwarf.actors.Moving

class AnimationFactory(resources: Resources) {
  def dwarfWalk = new DwarfWalk(resources)
  
  val playerAnimMap : Map[State, () => Animation] =
    Map(Moving() -> (() => dwarfWalk))
}

class DwarfWalk(resources: Resources) extends Animation(resources) {
  this.animId = 0
  this.assignFrameTimes(0.10f, 0.10f, 0.10f, 0.10f)
  this.totalFrameTime_ = this.frameTimes.fold(0f)(_ + _)
}



