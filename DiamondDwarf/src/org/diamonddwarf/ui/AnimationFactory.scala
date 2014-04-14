package org.diamonddwarf.ui

import org.diamonddwarf.ResourceLoader
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion
import com.badlogic.gdx.graphics.g2d.TextureRegion

final class AnimationFactory(private val resourceLoader: ResourceLoader) {
  val dwarfIdle = resourceLoader.atlas.findRegion("tileobj/dwarf")
  val dwarfWalk = resourceLoader.atlas.findRegions("tileobj/dwarf_walk")
  val dwarfDig = resourceLoader.atlas.findRegions("tileobj/dwarf_dig")

  def createDwarfMoveAnim = {
    val frames = for (i <- 0 until dwarfWalk.size) yield (dwarfWalk.get(i), 0.1f)
    new Animation(frames.toArray)
  }
  
  def createDwarDigAnim = {
    val frames = new Array[(TextureRegion,Float)](2);
    frames(0) = (dwarfDig.get(0), 1.5f)
    frames(1) = (dwarfDig.get(1), 1.7f)
    new Animation(frames)
  }
}