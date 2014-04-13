package org.diamonddwarf.ui

import org.diamonddwarf.ResourceLoader

final class AnimationFactory(private val resourceLoader: ResourceLoader) {
  val dwarfIdle = resourceLoader.atlas.findRegion("tileobj/dwarf")
  val dwarfWalk = resourceLoader.atlas.findRegions("tileobj/dwarf_walk")

  def createDwarfMoveAnim = {
    val frames = for (i <- 0 until dwarfWalk.size) yield (dwarfWalk.get(i), 0.2f)
    new Animation(frames.toArray)
  }
}