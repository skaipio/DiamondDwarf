package org.diamonddwarf.ui

import org.diamonddwarf.resources.ResourceLoader
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion

final class AnimationFactory(private val resourceLoader: ResourceLoader) {
  val dwarfIdle = resourceLoader.atlas.findRegion("tileobj/dwarf")
  val dwarfWalk = resourceLoader.atlas.findRegions("tileobj/dwarf_walk")
  val dwarfDig = resourceLoader.atlas.findRegions("tileobj/dwarf_dig")
  
  def createDwarfIdleAnim = {
    val frames = Array[(TextureRegion, Float)]((dwarfIdle, 1.0f))
    new Animation(frames)
  }

  def createDwarfMoveAnim = {
    val frames = for (i <- 0 until dwarfWalk.size) yield (dwarfWalk.get(i), 0.1f)
    new Animation(frames.toArray)
  }
  
  def createDwarfDigAnim = {
    val frames = new Array[(TextureRegion,Float)](3);
    frames(0) = (dwarfDig.get(0), 0.1f)
    frames(1) = (dwarfDig.get(1), 0.1f)
    frames(2) = (dwarfDig.get(2), 0.2f)
    new Animation(frames)
  }
}